package walaniam.scrabble.dictionary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class Dictionary {

    private final Permutations permutations = new Permutations();
    private final Words words;

    public Set<String> findWords(String letters) {
        return findWords(letters, null);
    }

    public Set<String> findWords(String letters, Integer wordLength) {

        long startTime = System.currentTimeMillis();
        log.debug("Searching matching words for letters: {}", letters);

        final Set<String> result;
        if (wordLength == null) {
            List<CompletableFuture<Set<String>>> futures = new ArrayList<>();
            for (int i = letters.length(); i >= 2; i--) {
                int permutationLength = i;
                futures.add(CompletableFuture.supplyAsync(
                        () -> permutations.matchWithPermutations(letters, permutationLength, words))
                );
            }
            CompletableFuture
                    .allOf(futures.stream().toArray(size -> new CompletableFuture[size]))
                    .join();

            result = new HashSet<>();
            futures.stream()
                    .map(CompletableFuture::join)
                    .forEach(result::addAll);
        } else {
            result = permutations.matchWithPermutations(letters, wordLength, words);
        }

        log.debug("{} words found in {} ms", result.size(), System.currentTimeMillis() - startTime);

        return result;
    }

    public List<String> findStartingWith(String prefix) {
        return findStartingWith(prefix, null);
    }

    public List<String> findStartingWith(String prefix, Integer wordLength) {

        final long startTime = System.currentTimeMillis();
        log.debug("Searching words starting with: {}", prefix);

        final List<String> result = new ArrayList<>();
        final char c = prefix.charAt(0);
        final int prefixLength = prefix.length();

        final Collection<String> startingWithWords = words.getWordsStartingWith(c);
        if (startingWithWords != null) {
            for (String word : startingWithWords) {
                int currentLength = word.length();
                if (word.startsWith(prefix)
                        && (wordLength == null || (wordLength != null && currentLength == wordLength))) {
                    result.add(word);
                }
                if (currentLength >= prefixLength
                        && word.substring(0, prefixLength).compareTo(prefix) > 0) {
                    break;
                }
            }
        }

        log.debug("{} found in {} ms", result.size(), System.currentTimeMillis() - startTime);

        return result;
    }

    public int totalWords() {
        return words.size();
    }

    public int longestWordLength() {
        return words.getLongestWordLength();
    }
}
