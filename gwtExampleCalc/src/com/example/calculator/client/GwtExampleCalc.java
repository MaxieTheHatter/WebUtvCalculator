package com.example.calculator.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.WindowImplIE.Resources;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class GwtExampleCalc implements EntryPoint {
	private final String SET_WIDTH = "130px";
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label header = new Label();
	private Label historyHeader = new Label();
	private TextBox result = new TextBox();
	private FlexTable numbers = new FlexTable();
	private FlexTable symbols = new FlexTable();
	private FlexTable history = new FlexTable();
	private String[] operators = new String[] { "+", "-", "*", "/", "%", "^", "=" };
	private String[] clearSymb = new String[] { "c", "ce" };
	private String[] bottomRow = new String[] { ".", "0", "+-" };
	private ArrayList<String> eqParts = new ArrayList<String>();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		int row = 0;
		int column = 0;

		for (int i = 1; i < 13; i++) {
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
				int adjusted = i;
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

			if ((i % 3) == 0) {
				row++;
				column = 0;
			}
		}

		// Manually add the symbols (operators) and clear sets
		// clear
		symbols.setWidget(2, 0, new Button(clearSymb[0], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(clearSymb[0]);
			}
		}));
		// clear all
		symbols.setWidget(2, 1, new Button(clearSymb[1], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(clearSymb[1]);
			}
		}));
		// calculate
				symbols.setWidget(2, 2, new Button(operators[6], new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						addToDisplay(operators[6]);
					}
				}));
				// calculate with enter key
				result.addKeyDownHandler(new KeyDownHandler() {

					@Override
					public void onKeyDown(KeyDownEvent event) {
						// TODO Auto-generated method stub
						if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
							addToDisplay(operators[6]);
					}
					
				});
		// addition
		symbols.setWidget(0, 0, new Button(operators[0], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[0]);
			}
		}));
		//subraction
		symbols.setWidget(0, 1, new Button(operators[1], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[1]);
			}
		}));
		// multiplication
		symbols.setWidget(0, 2, new Button(operators[2], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[2]);
			}
		}));
		//division
		symbols.setWidget(1, 0, new Button(operators[3], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[3]);
			}
		}));
		// modulo
		symbols.setWidget(1, 1, new Button(operators[4], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[4]);
			}
		}));
		// power of base
		symbols.setWidget(1, 2, new Button(operators[5], new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addToDisplay(operators[5]);
			}
		}));
		
		numbers.setWidth(SET_WIDTH);
		result.setWidth(SET_WIDTH);
		header.setText("Calculator");
		header.setWidth(SET_WIDTH);
		historyHeader.setText("History");
		history.setText(0, 0, "Query");
		history.setText(0, 1, "Answer");

		mainPanel.add(header);
		mainPanel.add(result);
		mainPanel.add(symbols);
		mainPanel.add(numbers);
		mainPanel.add(historyHeader);
		mainPanel.add(history);

		mainPanel.addStyleName("gwt-VerticalPanel");
		mainPanel.addStyleName("calc");
		result.setReadOnly(true);

		RootPanel.get("calc").add(mainPanel);

	}
	
	private void addToDisplay(String addText) {
		String origText = new String();
		origText = result.getText();
		result.setFocus(true);

		if (ArrayContains(operators, addText)) {
			if (addText == "=") {
				// figure it out and clear the past
				eqParts.add(origText);
				calculate();

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

	private double multiplication(double firstNum, double secondNum) {
		return firstNum * secondNum;
	}

	private double division(double firstNum, double secondNum) {
		return firstNum / secondNum;
	}

	private double modulo(double firstNum, double secondNum) {
		return firstNum % secondNum;
	}

	private double addition(double firstNum, double secondNum) {
		return firstNum + secondNum;
	}

	private double subtraction(double firstNum, double secondNum) {
		return firstNum - secondNum;
	}

	private double powerOf(double firstNum, double secondNum) {
		if (secondNum == 0 || secondNum < 0) {
			return 1;
		} else {
			return firstNum * powerOf(firstNum, secondNum - 1);
		}
	}

	private void calculate() {
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
				Window.alert(firstNum.toString());
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
				Window.alert(operator);
				continue;
			} else if (nextNum == null) {
				nextNum = Double.parseDouble(currVal);
				Window.alert(nextNum.toString());

				if (operator == "+") {
					answer = addition(firstNum, nextNum);
				} else if (operator == "-") {
					answer = subtraction(firstNum, nextNum);
				} else if (operator == "*") {
					answer = multiplication(firstNum, nextNum);
				} else if (operator == "/") {
					answer = division(firstNum, nextNum);
				} else if (operator == "^") {
					answer = powerOf(firstNum, nextNum);
				}
				else if (operator == "%"){
					answer = modulo(firstNum, nextNum);
				}

				result.setText(answer.toString());
				setHistory(firstNum, operator, nextNum, answer.toString());

				Window.alert("The answer is " + answer);
				firstNum = answer;
				nextNum = null;
				operator = null;
			} else {
				Window.alert("Something unexpected happened. Try again.");
			}
		}
	}
	
	private void setHistory(double first, String operator, double second, String answer) {
		int row = history.getRowCount();
		history.setText(row, 0, first + operator + second);
		history.setText(row, 1, answer);
		
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