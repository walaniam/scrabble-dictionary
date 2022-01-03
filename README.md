# scrabble-dictionary
Search scrabble game words

## How to use it?
```java
...
InputStream wordsFile = ...;
Words wordsSet = HashSetWords.open(wordsFile);
Dictionary dictionary = new Dictionary(words);

Set<String> searchResult = dictionary.findWords("hlleoowrld");
```