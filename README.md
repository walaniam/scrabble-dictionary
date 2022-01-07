# scrabble-dictionary
Search scrabble game words.

## How to use it?
```java
...
InputStream wordsFile = ...;
Words wordsSet = HashSetWords.open(wordsFile);
Dictionary dictionary = new Dictionary(words);

Set<String> searchResult = dictionary.findWords("hlleoowrld");
```

# Dictionaries
## Polish
### SJP
https://sjp.pl/slownik/growy/
## English
### Collins
Look here https://boardgames.stackexchange.com/questions/38366/latest-collins-scrabble-words-list-in-text-file
