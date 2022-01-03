# scrabble-dictionary
Search scrabble game words

## How to use it?
```java
...
InputStream wordsFile = ...;
Words wordsSet = HashSetWords.open(wordsFile);
Dictionary dictionary = new Dictionary(words);

dictionary.findWords("hlleoowrld");
```