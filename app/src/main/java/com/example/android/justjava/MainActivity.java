/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Check if the user wants whipped cream added to the coffee
     *
     * @return the user want whipped cream or not
     **/
    private boolean whippedCreamChecked() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        return checkBox.isChecked();
    }

    /**
     * Check if the user wants chocolate added to the coffee
     *
     * @return the user want chocolate or not
     **/
    private boolean chocolateChecked() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        return checkBox.isChecked();
    }

    /**
     * See the name tha the users type in
     *
     * @return the name of the user
     **/
    private String name() {
        EditText editText = (EditText) findViewById(R.id.name);
        return editText.getText().toString();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        /*displayMessage(createOrderSummary(calculatePrice()))*/
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name()));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(calculatePrice()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return price the total prices of the coffee
     */
    private int calculatePrice() {
        int basePrice = 5;
        if (whippedCreamChecked()) {
            basePrice = basePrice + 1;
        }
        if (chocolateChecked()) {
            basePrice = basePrice + 2;
        }
        int price = quantity * basePrice;
        return price;
    }

    /**
     * @param priceOfOrder tje total price of the coffee
     * @return a list of all the information
     */
    private String createOrderSummary(int priceOfOrder) {
        String nameString = getString(R.string.order_summary_name, name()) + "\n";
        String cream = getString(R.string.order_summary_whipped_cream, whippedCreamChecked()) + "\n";
        String chocolate = getString(R.string.order_summary_chocolate, chocolateChecked()) + "\n";
        String quantityString = getString(R.string.order_summary_quantity, quantity) + "\n";
        String totalString = getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(priceOfOrder)) + "\n";
        String thankYou = getString(R.string.thank_you);
        return nameString + cream + chocolate + quantityString + totalString + thankYou;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        //100 cups of coffee is the upper limit per user
        if (quantity < 100) {
            quantity = quantity + 1;
        } else if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "That's too much for us!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        //1 cup of coffee is the lower limit per user
        if (quantity > 1) {
            quantity = quantity - 1;
        } else if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "At least order 1 cup please!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffee) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffee);
    }
}