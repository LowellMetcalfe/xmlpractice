package com.metcalfe.lowell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.metcalfe.lowell.xmlpractice.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String position = null;
    int Middle = 0;
    long ISBN = 0;
    TextView textViewTitle;
    TextView textViewAuthor;
    TextView textViewGenre;
    RatingBar rating;
    TextView textViewDescription;
    ImageView coverImg;
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    Button FindButton;
    final Map<String, Integer> map = new HashMap<String, Integer>();
    final String[][] suggested = new String[20][3];
    final String[][] books = new String[20][7];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaybook);
        textViewTitle = (TextView) findViewById(R.id.Title);
        textViewAuthor = (TextView) findViewById(R.id.Author);
        textViewGenre = (TextView) findViewById(R.id.Genre);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        coverImg = (ImageView) findViewById(R.id.imageView);
        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        FindButton = (Button) findViewById(R.id.button);


        //GetBoook getbooks = new GetBoook();
        Random rand = new Random();
        books[0][0] = "0";
        books[0][1] = "Title";
        books[0][2] = "Author";
        books[0][3] = "Genre6";
        books[0][4] = "1";
        books[0][5] = "liescover";
        books[0][6] = "16";
        for (int i=1; i <20; i++){
            books[i][0] = Integer.toString(i);
            books[i][1] = "title" + i;
            books[i][2] = "Author" + i;
            books[i][3] = "Genre" + i;
            books[i][4] = Integer.toString(rand.nextInt(5)+1);
            books[i][5] = "codecover";
            books[i][6] = "59";
        }
        books [1][3] = "Genre6";
        books[1][5] = "hungercover";
        books [2][3] = "Genre6";
        books[2][5] = "gonecover";
        books [5][3] = "Genre6";
        books[5][5] = "lightcover";

        //for loop bellow checks if the sorting worked
        //for(int tester = 0; tester < 20; tester++){
        // 	System.out.println(books[tester][0]);
        //}

        //error with passing over the contents
        ISBN = 6;
        maps();
        //should return Middle and saves it here
        BinarySearch(books, ISBN);
        Middle =  BinarySearch(books, ISBN);
        suggestedBooks(books,Middle,suggested);;
        FindButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FindBook(position);
            }
        });
        imageButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ISBN = Long.parseLong(suggested[0][0]);
                BinarySearch(books, ISBN);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ISBN = Long.parseLong(suggested[1][0]);
                BinarySearch(books, ISBN);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ISBN = Long.parseLong(suggested[2][0]);
                BinarySearch(books, ISBN);
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ISBN = Long.parseLong(suggested[3][0]);
                BinarySearch(books, ISBN);
            }
        });
    }

    public int BinarySearch(String[][] books, long ISBN) {
        int Left = 0;
        int Right = books.length;
        int Middle = 0;
        boolean ItemFound = false;
        while (Left <= Right && ItemFound == false) {
            Middle = Math.round((Left + Right) / 2);
            //System.out.println(books[Middle][0]);
            long MiddleVal = Long.parseLong(books[Middle][0]);
            if (MiddleVal == ISBN) {
                ItemFound = true;
                System.out.println("ISBN: " + books[Middle][0]);
                textViewTitle.setText(books[Middle][1]);
                textViewAuthor.setText(books[Middle][2]);
                textViewGenre.setText(books[Middle][3]);
                rating.setRating(Integer.parseInt(books[Middle][4]));
                //there is an error bellow
                int resID = getResources().getIdentifier(books[Middle][5], "id", getPackageName());
                coverImg.setImageResource(resID);
                coverImg.setImageResource(map.get("codecover"));
                position = books[Middle][6];
            }
            //middle is greater than ISBN
            if (MiddleVal > ISBN) {
                Right = Middle - 1;
            }
            //middle is less than ISBN
            if (MiddleVal < ISBN) {
                Left = Middle + 1;
            }
        }
        return Middle;
    }

    public String Position() {
        return position;
    }

    //currently a simple XML
    public String[][] suggestedBooks(String[][] books, int Middle, String[][] suggested) {
        String Genre = books[Middle][3];
        int incrementor = 0;
        for (int i = 0; (i < 20) && (suggested != null||suggested[3][0].isEmpty()); i++) {
            if (Genre.equalsIgnoreCase(books[i][3])) {
                //0 is ISBN, 1 is the img and 2 is the rating
                suggested[incrementor][0] = books[i][0];
                suggested[incrementor][1] = books[i][5];
                suggested[incrementor][2] = books[i][4];
                incrementor = incrementor+1;
            }
        }
        int n = suggested.length;
        String temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i) && (suggested[j][2]!= null); j++) {
                //sign switch makes it descening instead of ascending
                if (Long.parseLong(suggested[j - 1][2]) < Long.parseLong(suggested[j][2])) {
                    //swap the elements!
                    temp = suggested[j - 1][2];
                    suggested[j - 1][2] = suggested[j][2];
                    suggested[j][2] = temp;
                }
            }
        }
        imageButton1.setImageResource(map.get(suggested[0][1]));
        imageButton2.setImageResource(map.get(suggested[1][1]));
        imageButton3.setImageResource(map.get(suggested[2][1]));
        imageButton4.setImageResource(map.get(suggested[3][1]));
        return suggested;
    }
    public void maps(){
        map.put("codecover", R.drawable.codecover);
        map.put("unknowncover", R.drawable.unknowncover);
        map.put("gonecover",R.drawable.gonecover);
        map.put("lightcover",R.drawable.hungercover);
        map.put("liescover",R.drawable.liescover);
        map.put("hungercover",R.drawable.hungercover);
        //list out all book image names here
        //isnt programitcally and wont work in sync with an active database
    }

    public void FindBook(String Position){

        setContentView(R.layout.find_book);
    ImageView myImageView = (ImageView) findViewById(R.id.imageView2);
        int Pos = Integer.parseInt(Position);
    if (Pos < 25) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan1);
    } else if (Pos > 25 && Pos < 50) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan2);
    } else if (Pos > 50 && Pos < 75) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan4);
    } else if (Pos > 75 && Pos < 100) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan5);
    } else if (Pos > 100 && Pos < 125) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan6);
    } else if (Pos > 125 && Pos < 150) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan7);
    } else if (Pos > 150 && Pos < 175) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan8);
    } else if (Pos > 175 && Pos < 200) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan9);
    }
    //bottom 4
    else if (Pos > 200 && Pos < 225) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan10);
    } else if (Pos > 225 && Pos < 250) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan11);
    } else if (Pos > 250 && Pos < 275) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan12);
    } else if (Pos > 275 && Pos < 300) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan13);
    } else if (Pos > 300 && Pos < 325) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan14);
    } else if (Pos > 325 && Pos < 350) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan15);
    } else if (Pos > 350 && Pos < 375) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan15);
    } else if (Pos > 375 && Pos < 400) {
        myImageView.setBackgroundResource(R.drawable.libraryfloorplan16);
    }
}


}