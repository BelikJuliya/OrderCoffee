/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package android.example.justjava;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private CheckBox toppingCheckBox;
    private CheckBox chocolateCheckBox;
    boolean isWhippedCream;
    boolean isChocolate;
    private int quantity = 0;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toppingCheckBox = findViewById(R.id.topping_checkbox);
        chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        isWhippedCream = toppingCheckBox.isChecked();
        isChocolate = chocolateCheckBox.isChecked();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        calculatePrice(quantity);
        sendEmail(createOrderSummary(price));
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view) {
        if (quantity <100) {
            display(++quantity);
        } else{
            Toast toast = Toast.makeText(this, getString(R.string.too_many_cups), Toast.LENGTH_LONG );
            toast.show();
        }
    }


    public void decrement(View view) {
        if (quantity > 0) {
            display(--quantity);
        } else{
            Toast toast = Toast.makeText(this, getString(R.string.too_few_cups), Toast.LENGTH_SHORT );
            toast.show();
        }
    }

    private void calculatePrice(int quantity) {
        CheckBox toppingCheckBox = findViewById(R.id.topping_checkbox);
        boolean isWhippedCream = toppingCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean isChocolate = chocolateCheckBox.isChecked();

        price = quantity * 5;
        if (isWhippedCream) {
            price++;
        }
        if (isChocolate) {
            price += 2;
        }
    }

    private String createOrderSummary(int price) {
        TextView nameTextView = findViewById(R.id.enter_name_edit_text);
        String name = nameTextView.getText().toString();
        return "Name: " + name + "\nAdd whipped cream? " + isWhippedCream + "\nAdd Chocolate? " + isChocolate + "\nQuantity: " + quantity + "\n" + "Total: $" + price + "\n" + "Thank you!";
    }

    private void sendEmail (String orderSummary){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("*/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Order");
        sendIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (sendIntent.resolveActivity(getPackageManager()) != null){
            startActivity(sendIntent);
        }
    }
}

