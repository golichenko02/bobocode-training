package ssl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;

import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NasaPicturesClient {

    private static final String NASA_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=16&api_key=c4zROfblZhCuC4eejuGJjT4nwvUZyBCsuCjfP0ax\n";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        List<String> pictures = getPictures();
        Pair<String, Long> result = findLargestPicture(pictures);
        System.out.println("src: " + result.getKey());
        System.out.println("size: " + result.getValue());
    }

    @SneakyThrows
    public static List<String> getPictures() {
        URL url = new URL(NASA_URL);
        try (Socket socket = SocketUtils.createSocketClient(url)) {
            return parseImagesResponse(SocketUtils.doGet(socket, url));
        }
    }

    public static Pair<String, Long> findLargestPicture(List<String> pictures) {
        return pictures.parallelStream()
                .collect(Collectors.toMap(Function.identity(), NasaPicturesClient::getPictureSize)).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .orElse(Pair.of("NOT_FOUND", -1L));
    }

    @SneakyThrows
    private static List<String> parseImagesResponse(String response) {
        return objectMapper.readTree(response).findValues("img_src").stream()
                .map(JsonNode::asText)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static Long getPictureSize(String imageUrl) {
        URL url = new URL(imageUrl);
        try (Socket socket = SocketUtils.createSocketClient(url)) {
            URL location = getLocation((SocketUtils.doHead(socket, url)));
            try (Socket redirectClient = SocketUtils.createSocketClient(location)) {
                return parseSizeResponse(SocketUtils.doHead(redirectClient, location));
            }
        }
    }

    @SneakyThrows
    private static URL getLocation(String response) {
        return new URL(response.lines()
                .filter(header -> header.toLowerCase().startsWith("location"))
                .findAny()
                .map(header -> header.split(": ")[1].trim())
                .orElse(""));
    }

    private static Long parseSizeResponse(String response) {
        return response.lines()
                .filter(header -> header.toLowerCase().contains("content-length"))
                .findAny()
                .map(header -> Long.parseLong(header.split(":")[1].trim()))
                .orElse(-1L);
    }
}