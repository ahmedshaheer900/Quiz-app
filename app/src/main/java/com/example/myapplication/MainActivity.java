package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionText, scoreText, timeRemaining;
    private RadioButton option1, option2, option3, option4;
    private RadioGroup optionGroup;
    private Button nextButton, prevButton, showAnswerButton;

    private String[] questions;  // Array of questions
    private String[][] options;  // Array of options
    private String[] correctAnswers;  // Array of correct answers

    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean hasSeenAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        timeRemaining = findViewById(R.id.timeRemaining);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        optionGroup = findViewById(R.id.optionGroup);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        showAnswerButton = findViewById(R.id.showAnswerButton);
        Button endQuizButton = findViewById(R.id.endQuizButton);

        // Sample data
        questions = new String[] {
                "What is the name of the main character in 'The Legend of Zelda' series?",
                "In which game do you battle with Pokémon?",
                "What is the setting of 'Minecraft'?",
                "Which game features a 'Battle Royale' mode called 'Warzone'?",
                "What is the name of Mario's brother?",
                "In which game can you build and manage your own theme park?",
                "What is the primary objective in 'Fortnite'?",
                "Which game is known for its open-world exploration and car customization?",
                "Who is the creator of the 'Metal Gear' series?",
                "In which game do you play as a bounty hunter in space?",
                "What is the name of the virtual city in 'Grand Theft Auto V'?",
                "In which game do you fight as superheroes in a team?",
                "What is the primary gameplay style of 'The Witcher 3'?",
                "Which game series features a protagonist named Lara Croft?",
                "What is the goal in 'Among Us'?",
                "Which game is set in a post-apocalyptic world with zombies?",
                "What game features a yellow, circular character known as Pac-Man?",
                "Which game allows you to manage a football team and play matches?",
                "In which game do you solve mysteries as a private detective in Los Angeles?"
        };

        options = new String[][] {
                {"Link", "Zelda", "Ganondorf", "Nabooru"},
                {"Pokémon Go", "Pokémon Sword and Shield", "Pokémon Snap", "Pokémon Mystery Dungeon"},
                {"Fantasy Kingdom", "Survival Island", "Post-Apocalyptic City", "Randomly Generated World"},
                {"Call of Duty", "Battlefield", "PUBG", "Apex Legends"},
                {"Luigi", "Wario", "Yoshi", "Toad"},
                {"RollerCoaster Tycoon", "SimCity", "Planet Coaster", "Zoo Tycoon"},
                {"Survive until the end", "Complete missions", "Collect resources", "Build structures"},
                {"Forza Horizon", "Need for Speed", "Gran Turismo", "Burnout"},
                {"Hideo Kojima", "Shigeru Miyamoto", "John Carmack", "Sid Meier"},
                {"Star Wars: Knights of the Old Republic", "Mass Effect", "No Man's Sky", "Star Trek Online"},
                {"Los Santos", "Vice City", "Liberty City", "San Andreas"},
                {"Overwatch", "League of Legends", "Dota 2", "Counter-Strike"},
                {"Role-Playing Game", "First-Person Shooter", "Strategy", "Puzzle"},
                {"Uncharted", "Assassin's Creed", "Tomb Raider", "Far Cry"},
                {"Complete tasks and identify impostors", "Survive as long as possible", "Build structures", "Gather resources"},
                {"The Last of Us", "Resident Evil", "Left 4 Dead", "DayZ"},
                {"Pac-Man", "Donkey Kong", "Galaga", "Space Invaders"},
                {"FIFA", "Football Manager", "PES", "Rocket League"},
                {"L.A. Noire", "Murdered: Soul Suspect", "Sherlock Holmes: Crimes and Punishments", "The Wolf Among Us"}
        };

        correctAnswers = new String[] {
                "Link",
                "Pokémon Sword and Shield",
                "Randomly Generated World",
                "Call of Duty",
                "Luigi",
                "RollerCoaster Tycoon",
                "Survive until the end",
                "Forza Horizon",
                "Hideo Kojima",
                "No Man's Sky",
                "Los Santos",
                "Overwatch",
                "Role-Playing Game",
                "Tomb Raider",
                "Complete tasks and identify impostors",
                "The Last of Us",
                "Pac-Man",
                "Football Manager",
                "L.A. Noire"
        };

        // Start countdown timer for 10 minutes
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Calculate minutes and seconds from millisUntilFinished
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                // Update the timer display
                timeRemaining.setText(String.format("Time: %02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                // End the quiz when the timer finishes
                endQuiz();
            }
        }.start();
        // Set OnClickListener for nextButton
        nextButton.setOnClickListener(v -> {
            // Get selected option
            int selectedOptionId = optionGroup.getCheckedRadioButtonId();
            if (selectedOptionId != -1) {
                RadioButton selectedOption = findViewById(selectedOptionId);
                String userAnswer = selectedOption.getText().toString();

                // Check if the selected answer is correct
                if (userAnswer.equals(correctAnswers[currentQuestionIndex])) {
                    score += 5; // Add 5 for correct answer
                } else {
                    score -= 1; // Deduct 1 for incorrect answer
                }

                // Update score display
                scoreText.setText("Score: " + score);
            }

            // Load next question
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                loadQuestion();
            }
        });
        endQuizButton.setOnClickListener(v -> endQuiz());
        // Prev Button
        prevButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });

        // Show Answer Button
        showAnswerButton.setOnClickListener(v -> {
            hasSeenAnswer = true;
            // Deduct 1 point for showing the answer
            score -= 1;
            scoreText.setText("Score: " + score);
            // Show correct answer
            // Logic to highlight/show the correct answer
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        option1.setText(options[currentQuestionIndex][0]);
        option2.setText(options[currentQuestionIndex][1]);
        option3.setText(options[currentQuestionIndex][2]);
        option4.setText(options[currentQuestionIndex][3]);

        // Reset the selection
        optionGroup.clearCheck();
    }

    private void endQuiz() {
        // Calculate percentage
        int totalQuestions = questions.length;
        double percentage = ((double) score / (totalQuestions * 5)) * 100;

        // Display total marks and percentage in a dialog or on the main screen
        String resultMessage = "Quiz Finished!\n" +
                "Total Score: " + score + "\n" +
                "Percentage: " + percentage + "%";

        // You can display this in a dialog or simply in a TextView
        new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("Quiz Completed")
                .setMessage(resultMessage)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }
}


