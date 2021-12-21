package walaniam.scrabble.dictionary.set;

import lombok.RequiredArgsConstructor;
import walaniam.scrabble.dictionary.Words;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static walaniam.scrabble.dictionary.StringFunctions.TO_LOWERCASE;

@RequiredArgsConstructor
public class HashSetWords implements Words {

    private final Set<String> wordsSet;
    private final int longestWordLength;

    public static HashSetWords open(InputStream wordsStream) throws IOException {
        WordsReader reader = new WordsReader(wordsStream, TO_LOWERCASE);
        Set<String> consumed = new HashSet<>();
        AtomicInteger longest = new AtomicInteger();
        reader.read(word -> {
            consumed.add(word);
            longest.set(Math.max(longest.get(), word.length()));
        });
        return new HashSetWords(consumed, longest.get());
    }

    @Override
    public boolean contains(String word) {
        return wordsSet.contains(TO_LOWERCASE.apply(word));
    }

    @Override
    public int getLongestWordLength() {
        return longestWordLength;
    }

    @Override
    public Collection<String> getWordsStartingWith(char c) {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {
        return wordsSet.size();
    }
}
