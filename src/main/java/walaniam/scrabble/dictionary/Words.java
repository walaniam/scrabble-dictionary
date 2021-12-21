package walaniam.scrabble.dictionary;

import java.util.Collection;

public interface Words {
    boolean contains(String word);
    int size();
    int getLongestWordLength();
    Collection<String> getWordsStartingWith(char c);
}
