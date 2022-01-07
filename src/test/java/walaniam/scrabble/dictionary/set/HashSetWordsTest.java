package walaniam.scrabble.dictionary.set;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import walaniam.scrabble.dictionary.Words;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HashSetWordsTest {

    private final HashSetWords underTest = HashSetWords.open(ImmutableSet.of(
            "aa", "aaa", "aba", "abb", "cba", "dada", "dabada"
    ));

    @Test
    void findStartingWith() {
        assertThat(underTest.getWordsStartingWith('c')).containsExactlyInAnyOrder("cba");
        assertThat(underTest.getWordsStartingWith('d')).containsExactlyInAnyOrder("dada", "dabada");
        assertThat(underTest.getWordsStartingWith('e')).isEmpty();
    }

    @Test
    void openFromStream() throws IOException {
        try (InputStream wordsStream = this.getClass().getResourceAsStream("/words.txt")) {
            Words words = HashSetWords.open(wordsStream);
            assertThat(words.size()).isEqualTo(100);
            assertThat(words.getLongestWordLength()).isEqualTo(13);
            assertThat(words.getWordsStartingWith('a')).hasSize(50);
            assertThat(words.contains("abakan")).isTrue();
            assertThat(words.contains("żyzny")).isTrue();
        }
    }

}