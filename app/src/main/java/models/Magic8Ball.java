package models;

import java.util.ArrayList;
import java.util.Random;

public class Magic8Ball {

    private static final String[] answers = {
            "It is certain.",
            "It is decidedly so.",
            "Without a doubt.",
            "Yes - definitely.",
            "You may rely on it.",
            "As I see it, yes.",
            "Most likely.",
            "Outlook good.",
            "Signs point to yes.",
            "Yes.",
            "Reply hazy, try again.",
            "Ask again later.",
            "Better not tell you now.",
            "Cannot predict now.",
            "Concentrate and ask again.",
            "Don't count on it.",
            "My sources are unclear.",
            "Doubtful.",
            "Not likely.",
            "Machlokes.",
            "Very doubtful.",
            "Don't count on it.",
            "My reply is no.",
            "Outlook not so good.",
            "Very doubtful."
    };

    private static int questionCount;
    private static ArrayList<String> questionList = new ArrayList<>();
    public Magic8Ball() {
        questionCount = 0;
    }

    public static String answerQuestion() {
        questionCount++;
        Random random = new Random();
        int answerIndex = random.nextInt(answers.length);
        return answers[answerIndex];
    }

    public static int getQuestionCount() {
        return questionCount;
    }

    public static void saveQuestion(String question) {
        questionList.add(question);
    }

    public static String getSavedQuestions() {
        String askedQuestions = "";
        int questionListSize = questionList.size();
        if(questionListSize >0) {
            for(int i = 0; i<questionList.size();i++) {
                askedQuestions += questionList.get(i) + "\n";
            }
        } else {
            askedQuestions = "No saved questions.";
        }
        return askedQuestions;
    }

    public static void deleteSavedQuestions() {
        questionList.clear();
        questionCount = 0;
    }

}
