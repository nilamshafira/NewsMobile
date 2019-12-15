package com.example.newsmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class jual extends AppCompatActivity implements View.OnClickListener {
    private Button btnBack;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 Newspaper", Toast.LENGTH_SHORT).show();
            //exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 Newspaper", Toast.LENGTH_SHORT).show();
            //exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find the user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox MagazineCheckBox = (CheckBox) findViewById(R.id.magazine_checkbox);
        boolean hasMagazine = MagazineCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox TabloidCheckBox = (CheckBox) findViewById(R.id.tabloid_checkbox);
        boolean hasTabloid = TabloidCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasMagazine, hasTabloid);

        // Display the order summary on the screen
        String priceMessage = createOrderSummary(name, price, hasMagazine, hasTabloid);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "News Mobile order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addMagazine, boolean addTabloid) {
        int basePrice = 0;
        if (addMagazine) {
            basePrice = basePrice + 10000;
        }
        if (addTabloid) {
            basePrice = basePrice + 15000;
        }
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addMagazine is whether or not to add whipped cream to the coffee
     * @param addTabloid    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addMagazine, boolean addTabloid) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_magazine, addMagazine);
        priceMessage += "\n" + getString(R.string.order_summary_tabloid, addTabloid);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.Thank_You);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}