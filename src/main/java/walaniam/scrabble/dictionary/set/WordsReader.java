package walaniam.scrabble.dictionary.set;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
class WordsReader {

    public static final String ENCODING = "utf-8"; //"windows-1250";

    private static final Function<StringBuilder, String> READ_BUFFER = wordBuffer -> {
        int length = wordBuffer.length();
        String result = "";
        if (length > 0) {
            result = wordBuffer.toString();
            wordBuffer.delete(0, length);
        }
        return result;
    };

    private final InputStream wordsStream;

    public void read(Consumer<String> consumer) throws IOException {

        final long start = System.currentTimeMillis();
        int wordsLoaded = 0;

        log.debug("Loading words from stream...");

        try (BufferedReader br = new BufferedReader(readWithEncoding(wordsStream))) {
            final StringBuilder wordBuffer = new StringBuilder();
            // 100kB buffer
            final int bufferSize = 100 * 1024;
            final char[] readBuffer = new char[bufferSize];
            int read = -1;

            // read characters from buffer
            while ((read = br.read(readBuffer, 0, bufferSize)) > -1) {
                for (int i = 0; i < read; i++) {
                    // create words from characters and add them to list
                    char c = readBuffer[i];
                    if (!Character.isWhitespace(c)) {
                        wordBuffer.append(c);
                    } else {
                        if (consume(consumer, wordBuffer)) {
                            wordsLoaded++;
                        }
                    }
                }
            }

            if (consume(consumer, wordBuffer)) {
                wordsLoaded++;
            }
        }

        log.debug("{} words loaded in {} ms", wordsLoaded, System.currentTimeMillis() - start);
    }

    private boolean consume(Consumer<String> consumer, StringBuilder buffer) {
        String word = READ_BUFFER.apply(buffer);
        if (word != null && word.length() > 0) {
            consumer.accept(word);
            return true;
        }
        return false;
    }

    private Reader readWithEncoding(InputStream input) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(input);
        CharsetDetector detector = new CharsetDetector();
        detector.setText(bis);
        CharsetMatch charsetMatch = detector.detect();

        if (charsetMatch != null) {
            log.debug("Detected charset={}", charsetMatch.getName());
            return charsetMatch.getReader();
        } else {
            log.warn("Could not autodetect charset. Default to {}", ENCODING);
            return new InputStreamReader(input, ENCODING);
        }
    }
}
