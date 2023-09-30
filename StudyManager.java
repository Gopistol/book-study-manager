import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Sentence {

  private String content;
  private boolean isBold = false;
  private int page;

  public Sentence(String sentence) {
    String[] tokens = sentence.split("\\(");
    this.content = tokens[0].replace("-", "").trim();
    this.page = Integer.parseInt(tokens[1].replace(")", ""));
  }


  @Override
  public String toString() {
    return isBold ? "- **" + content + " (" + page + ")**" : "- " + content + " (" + page + ")";
  }

  public String getContent() {
    return this.content;
  }

  public void setBold(boolean bold) {
    isBold = bold;
  }
}

public class StudyManager {

  public static void main(String[] args) {
    // 스터디원 n명이 중요하다고 생각한 문장을 색깔별로 입력받음
    // 두 명 이상이 겹치는 문장은 bold 처리 문장으로 개별 분리함
    // 한 번만 나온 문장은 따로 분리
    List<Sentence> sentences = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine()) {
      String stringSentence = sc.nextLine();
      // q를 입력받으면 종료
      if (Objects.equals(stringSentence, "q")) {
        break;
      }
      if (stringSentence.equals("")) {
        continue;
      }
      Sentence sentence = new Sentence(stringSentence);
      if (isDuplicatedSentence(sentences, stringSentence)) {
        for (Sentence sentence1 : sentences) {
          String sentenceTostring = sentence1.toString();
          if (sentenceTostring.equals(stringSentence)) {
            sentence1.setBold(true);
          }
        }
      } else {
        sentences.add(sentence);
      }
    }
    sc.close();

    // 결과 출력
    for (Sentence sentence : sentences) {
      System.out.println(sentence);
    }
  }

  public static boolean isDuplicatedSentence(List<Sentence> sentences, String target) {
    // 각 문장 별 단어가 반절 이상이 같으면 겹치는 문장이라고 판단
    List<String> targetTokens = List.of(target.replace("-", "").trim().split("\\s+"));
    for (Sentence sentence : sentences) {
      List<String> sentenceTokens = Arrays.stream(sentence.getContent().split("\\s+"))
          .collect(Collectors.toList());
      long commonTokensCount = IntStream.range(0,
              Math.min(targetTokens.size(), sentenceTokens.size()))
          .filter(i -> Objects.equals(targetTokens.get(i), sentenceTokens.get(i))).count();
      if (commonTokensCount > Math.min(targetTokens.size(), sentenceTokens.size()) / 2) {
        return true;
      }
    }
    return false;
  }
}
