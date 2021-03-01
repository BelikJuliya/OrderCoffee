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
    private boolean isWhippedCream;
    private boolean isChocolate;
    private int mQuantity = 0;
    private int mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox toppingCheckBox = findViewById(R.id.topping_checkbox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        isWhippedCream = toppingCheckBox.isChecked();
        isChocolate = chocolateCheckBox.isChecked();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        calculatePrice(mQuantity);
        sendEmail(createOrderSummary(mPrice));
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view) {
        if (mQuantity < 100) {
            display(++mQuantity);
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.too_many_cups), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void decrement(View view) {
        if (mQuantity > 0) {
            display(--mQuantity);
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.too_few_cups), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void calculatePrice(int quantity) {
        CheckBox toppingCheckBox = findViewById(R.id.topping_checkbox);
        boolean isWhippedCream = toppingCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean isChocolate = chocolateCheckBox.isChecked();

        mPrice = quantity * 5;
        if (isWhippedCream) {
            mPrice++;
        }
        if (isChocolate) {
            mPrice += 2;
        }
    }

    private String createOrderSummary(int price) {
        TextView nameTextView = findViewById(R.id.enter_name_edit_text);
        String name = nameTextView.getText().toString();
        return "Name: " + name + "\nAdd whipped cream? " + isWhippedCream + "\nAdd Chocolate? " + isChocolate + "\nQuantity: " + mQuantity + "\n" + "Total: $" + price + "\n" + "Thank you!";
    }

    private void sendEmail(String orderSummary) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("*/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Order");
        sendIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }
}

