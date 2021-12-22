package walaniam.scrabble.dictionary;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DictionaryTest {

    @Mock
    private Words words;

    @InjectMocks
    private Dictionary underTest;

    @Test
    void findWords() {
        // given
        lenient().when(words.contains("aa")).thenReturn(true);
        lenient().when(words.contains("ba")).thenReturn(true);
        lenient().when(words.contains("aaa")).thenReturn(true);
        lenient().when(words.contains("aba")).thenReturn(true);
        lenient().when(words.contains("baa")).thenReturn(true);
        lenient().when(words.contains("baab")).thenReturn(true);
        when(words.getLongestWordLength()).thenReturn(4);
        // when - then
        assertThat(underTest.findWords("aab", 2)).containsExactlyInAnyOrder("aa", "ba");
        assertThat(underTest.findWords("aab", 3)).containsExactlyInAnyOrder("aba", "baa");
        assertThat(underTest.findWords("aab")).containsExactlyInAnyOrder("aa", "ba", "aba", "baa");
    }

    @Test
    void findStartingWith() {
        // given
        lenient().when(words.getWordsStartingWith('a')).thenReturn(ImmutableSet.of("aaa", "aa", "aba"));
        lenient().when(words.getWordsStartingWith('b')).thenReturn(ImmutableSet.of("ba", "baa", "baab"));
        assertThat(underTest.findStartingWith("a")).containsExactlyInAnyOrder("aa", "aaa", "aba");
    }

    @Test
    void totalWords() {
        when(words.size()).thenReturn(101);
        assertThat(underTest.totalWords()).isEqualTo(101);
    }

    @Test
    void longestWordLength() {
        when(words.getLongestWordLength()).thenReturn(14);
        assertThat(underTest.longestWordLength()).isEqualTo(14);
    }
}