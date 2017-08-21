package com.example.calculator.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class GwtExampleCalc implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private Label header = new Label();
	private TextBox result = new TextBox();
	private FlexTable numbers = new FlexTable();
	private FlexTable clear = new FlexTable();
	private FlexTable history = new FlexTable();
	private TextBox operand1TextBox = new TextBox();
	private TextBox operand2TextBox = new TextBox();
	private Button calculateButton = new Button("Calculate");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox operatorTextBox = new SuggestBox(oracle);
	private String[] operators = new String[] { "+", "-", "*", "/", "%", "e<sup>x</sup>", "=" };
	private String[] clearSymb = new String[] { "c", "ce" };
	private String[] bottomRow = new String[] { ",", "0", "+-" };
	private ArrayList<String> eqParts = new ArrayList<String>();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		int row = 0;
		int column = 0;

		for (int i = 0; i < 16; i++) {
			Button btn = null;
			if (row == 0) {
				final String value = String.valueOf(i);
				btn = new Button(value);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						addToDisplay(value);

					}

				});
			} else if (row == 3) {
				if (i != 16) {
					final String value = bottomRow[column];
					btn = new Button(value);
					btn.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							addToDisplay(value);

						}

					});
				}
			} else {
				int adjusted = i - row;
				final String value = String.valueOf(adjusted);
				btn = new Button(value);
				btn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						addToDisplay(value);

					}

				});
			}

			numbers.setWidget(row, column, btn);
			column++;

			if ((i % 4) == 0) {
				row++;
				column = 0;
			}
		}

		// Manually add the symbols (operators) and clear sets
		clear.setWidget(0, 0, new Button(clearSymb[0], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(clearSymb[0]);
			}
		}));

		clear.setWidget(0, 0, new Button(clearSymb[1], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(clearSymb[1]);
			}
		}));

		clear.setWidget(0, 3, new Button(operators[0], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[0]);
			}
		}));

		clear.setWidget(0, 3, new Button(operators[1], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[1]);
			}
		}));

		clear.setWidget(1, 3, new Button(operators[2], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[2]);
			}
		}));

		clear.setWidget(2, 3, new Button(operators[3], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[3]);
			}
		}));

		clear.setWidget(3, 3, new Button(operators[4], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[4]);
			}
		}));

		clear.setWidget(3, 3, new Button(operators[5], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[5]);
			}
		}));

		clear.setWidget(3, 3, new Button(operators[6], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[6]);
			}
		}));

		numbers.setWidth("130px");
		result.setWidth("130px");
		header.setText("Calculator");
		header.setWidth("130px");

		// Suggests the valid operators
		oracle.add("*");
		oracle.add("%");
		oracle.add("+");
		oracle.add("-");
		oracle.add("^");
		oracle.add("/");

		mainPanel.add(header);
		mainPanel.add(result);
		mainPanel.add(clear);
		mainPanel.add(numbers);

		mainPanel.addStyleName("gwt-VerticalPanel");
		mainPanel.addStyleName("calc");

		RootPanel.get("calc").add(mainPanel);
		// addPanel.add(operand1TextBox);
		// addPanel.add(operatorTextBox);
		// addPanel.add(operand2TextBox);
		// addPanel.add(calculateButton);

		// TODO Assemble Main panel.

		// mainPanel.add(addPanel); // TODO Associate the Main panel with the HTML host
		// page.
		// RootPanel.get("calc").add(mainPanel); // TODO Move cursor focus to the input
		// box.

		calculateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				calculate();
			}
		});

	}

	private void addToDisplay(String addText) {
		String origText = new String();
		origText = result.getText();

		if (ArrayContains(operators, addText)) {
			if (addText == "=") {
				// figure it out and clear the past
				eqParts.add(origText);
				FigureItOut();

				eqParts.clear();
				return;
			} else // anything else
			{
				// add to array
				if (result.getText() != null)
					eqParts.add(origText);
				eqParts.add(addText);
				result.setText("");
				return;
			}
		}

		if (addText == "+-") {
			if (origText.indexOf("-") > -1)
				result.setText(origText.substring(1, origText.length()));
			else
				result.setText("-" + origText);
		} else {
			if (addText == "%") {
				Double percent = null;
				percent = Double.parseDouble(origText) / 100;

				result.setText(percent.toString());
				return;
			}
			if (addText == "c") {
				result.setText("");
				return;
			}
			if (addText == "ce") {
				result.setText("");
				eqParts.clear();
				return;
			}
			if (addText == ".") {
				if (origText.indexOf(".") > -1)
					return;
				else
					result.setText(origText + addText);
			} else {
				result.setText(origText + addText);
			}
		}
	}

	private long multiplication(long firstNum, long secondNum) {
		return firstNum * secondNum;
	}

	private long division(long firstNum, long secondNum) {
		return firstNum / secondNum;
	}

	private long modulo(long firstNum, long secondNum) {
		return firstNum % secondNum;
	}

	private long addition(long firstNum, long secondNum) {
		return firstNum + secondNum;
	}

	private long subtraction(long firstNum, long secondNum) {
		return firstNum - secondNum;
	}

	private long powerOf(long firstNum, long secondNum) {
		if (secondNum == 0 || secondNum < 0) {
			return 1;
		} else {
			return firstNum * powerOf(firstNum, secondNum - 1);
		}
	}

	private void calculate() {

		final String operator = operatorTextBox.getText().trim();
		calculateButton.setFocus(true);
		if ((!operator.equals("*") && !operator.equals("+") && !operator.equals("%") && !operator.equals("-")
				&& !operator.equals("/") && !operator.equals("^")) || !isNumber(operand1TextBox.getText().trim())
				|| !isNumber(operand2TextBox.getText().trim())) {
			Window.alert("You have entered a non valid binary operator or one of the operands is not an integer");

			return;
		}

		long first = Long.parseLong(operand1TextBox.getText());
		long second = Long.parseLong(operand2TextBox.getText());
		long answer;
		// Multiplication
		if (operator.equals("*")) {
			answer = multiplication(first, second);
			Window.alert("The answer is: " + answer);

		}
		// Modulo
		else if (operator.equals("%")) {
			answer = modulo(first, second);
			Window.alert("The answer is: " + answer);

		}
		// addition
		else if (operator.equals("%")) {
			answer = addition(first, second);
			Window.alert("The answer is: " + answer);

		}
		// division
		else if (operator.equals("/")) {
			answer = division(first, second);
			Window.alert("The answer is: " + answer);

		}
		// substraction
		else if (operator.equals("-")) {
			answer = subtraction(first, second);
			Window.alert("The answer is: " + answer);

		}
		// to the power of
		else {
			answer = powerOf(first, second);
			Window.alert("The answer is: " + answer);
		}

	}

	// Checkes if a String could be seen as an integer
	public boolean isNumber(String input) {
		try {
			Long.parseLong(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void FigureItOut() {
		// set displayArea text to the answer
		Double answer = null;
		Double firstNum = null;
		Double nextNum = null;
		String operator = null;

		for (int i = 0; i < eqParts.size(); i++) {
			String currVal = eqParts.get(i);
			boolean isOperator = false;
			isOperator = ArrayContains(operators, currVal);

			// add a number
			if (firstNum == null) {
				firstNum = Double.parseDouble(currVal);
				// Window.alert(firstNum.toString());
				continue;
			} else if (isOperator == true && i == 0) {
				Window.alert("Try again!");
				eqParts.clear();
				return;
			} else if (isOperator == true) {
				if (i == eqParts.size()) {
					Window.alert("You may not end with an operator");
					eqParts.clear();
					result.setText("");
					return;
				}
				operator = currVal;
				// Window.alert(operator);
				continue;
			} else if (nextNum == null) {
				nextNum = Double.parseDouble(currVal);
				// Window.alert(nextNum.toString());

				if (operator == "+") {
					answer = firstNum + nextNum;
				} else if (operator == "-") {
					answer = firstNum - nextNum;
				} else if (operator == "x") {
					answer = firstNum * nextNum;
				} else if (operator == "/") {
					answer = firstNum / nextNum;
				} else if (operator == "=") {
				} // do nothing?
				else {
				} // do someting?

				result.setText(answer.toString());

				// Window.alert("The answer is " + answer);
				firstNum = answer;
				nextNum = null;
				operator = null;
			} else {
				Window.alert("Something unexpected happened. Try again.");
			}
		}
	}

	private boolean ArrayContains(String[] array, String check) {
		boolean found = false;
		for (int i = 0; i < array.length; i++) {
			if (check == array[i])
				return true;
		}

		return found;
	}

}