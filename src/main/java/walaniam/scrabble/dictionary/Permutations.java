package walaniam.scrabble.dictionary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static walaniam.scrabble.dictionary.StringFunctions.CHARS_TO_STRING;
import static walaniam.scrabble.dictionary.StringFunctions.TO_LOWERCASE;

@Slf4j
@RequiredArgsConstructor
class Permutations {

    public Set<String> matchWithPermutations(String letters, int k, Words words) {
        return choosePermutations(TO_LOWERCASE.apply(letters).toCharArray(), k, words);
    }

    private Set<String> choosePermutations(char[] chars, int k, Words words) {

        final int longestWord = words.getLongestWordLength();

        if (longestWord < 1) {
            return Collections.emptySet();
        }

        final long startTime = System.currentTimeMillis();

        final int n = chars.length;
        if (k > n) {
            throw new IllegalArgumentException("k is greater than elementsSeq length");
        }

        if (log.isDebugEnabled()) {
            log.debug("Searching {} element combination from {}", k, new String(chars));
        }

        final int numOfSearched = (int) (factorial(n) / (factorial(n - k)));

        final Set<String> matched = new HashSet<>();

        if (log.isDebugEnabled()) {
            log.debug("Enumerating char sequence. Chars: "
                    + Arrays.toString(chars) + ", wordsBuffer size: "
                    + longestWord);
        }

        enumerateCharSequence(chars, n, k, matched, new char[longestWord], words);

        if (words == null && numOfSearched != matched.size()) {
            throw new IllegalStateException("Expected number of permutations was " + numOfSearched
                    + " but " + matched.size() + " was found");
        }

        log.debug("{} permutations found in {} ms", numOfSearched, System.currentTimeMillis() - startTime);

        return matched;
    }

    private static void enumerateCharSequence(char[] chars, int n, int k, Set<String> matched,
                                              char[] word, Words words) {

        if (k == 0) {

            int wordLength = 0;
            for (int i = n; i < chars.length; i++) {
                word[wordLength++] = chars[i];
            }

            if (wordLength > 0) {
                String wordAsString = CHARS_TO_STRING.apply(word, wordLength);
                if (words.contains(wordAsString)) {
                    matched.add(wordAsString);
                }
            }
            return;
        }

        for (int i = 0; i < n; i++) {
            swap(chars, i, n - 1);
            enumerateCharSequence(chars, n - 1, k - 1, matched, word, words);
            swap(chars, i, n - 1);
        }
    }

    // helper function that swaps a[i] and a[j]
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    private static long factorial(final int n) {

        if (n < 0) {
            throw new IllegalArgumentException("n < 0");
        }

        int nCopy = n;
        long factorial = 1;

        while (nCopy > 0) {
            factorial = factorial * nCopy--;
        }

        return factorial;
    }

}
