package walaniam.scrabble.dictionary.set;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WordsReaderTest {

    @Test
    void read() throws IOException {
        try (InputStream wordsStream = this.getClass().getResourceAsStream("/words.txt")) {
            WordsReader reader = new WordsReader(wordsStream);
            Set<String> loaded = new HashSet<>();
            reader.read(loaded::add);
            assertThat(loaded).hasSize(100);
            assertThat(loaded).contains("abakan", "żyzny");
            assertThat(loaded).doesNotContain("");
        }
    }
}