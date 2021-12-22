package walaniam.scrabble.dictionary.set;

import com.google.common.collect.HashMultimap;
import lombok.RequiredArgsConstructor;
import walaniam.scrabble.dictionary.Words;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static walaniam.scrabble.dictionary.StringFunctions.TO_LOWERCASE;

@RequiredArgsConstructor
public class HashSetWords implements Words {

    private final Set<String> wordsSet;
    private final HashMultimap byLetter;
    private final int longestWordLength;

    public static HashSetWords open(InputStream wordsStream) throws IOException {
        WordsReader reader = new WordsReader(wordsStream);
        WordConsumer consumer = new WordConsumer();
        reader.read(consumer);
        return new HashSetWords(consumer.consumed, consumer.byLetter, consumer.longest);
    }

    public static HashSetWords open(Collection<String> input) {
        WordConsumer consumer = new WordConsumer();
        input.forEach(consumer);
        return new HashSetWords(consumer.consumed, consumer.byLetter, consumer.longest);
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
        return Collections.unmodifiableSet(byLetter.get(c));
    }

    @Override
    public int size() {
        return wordsSet.size();
    }

    private static class WordConsumer implements Consumer<String> {

        private final Set<String> consumed = new HashSet<>();
        private final HashMultimap byLetter = HashMultimap.create();
        private int longest;

        @Override
        public void accept(String word) {
            String toAdd = TO_LOWERCASE.apply(word);
            char firstLetter = toAdd.charAt(0);
            consumed.add(toAdd);
            byLetter.put(firstLetter, toAdd);
            longest = Math.max(longest, toAdd.length());
        }
    }
}
