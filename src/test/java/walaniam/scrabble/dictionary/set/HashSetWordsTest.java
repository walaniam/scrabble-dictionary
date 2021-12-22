package walaniam.scrabble.dictionary.set;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

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

}