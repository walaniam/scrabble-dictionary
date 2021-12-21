package walaniam.scrabble.dictionary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringFunctions {

    public static final Function<String, String> TO_LOWERCASE = String::toLowerCase;

    public static final BiFunction<char[], Integer, String> CHARS_TO_STRING = (chars, size) -> {
        String result;
        if (size == chars.length) {
            result = new String(chars);
        } else {
            result = new String(chars, 0, size);
        }
        return result;
    };
}
