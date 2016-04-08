package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe implements ActionListener{
final String VERSION = "2.0";
//Setting up ALL the variables
JFrame window = new JFrame("Tic-Tac-Toe "+ VERSION);
JMenuBar mnuMain = new JMenuBar();
JMenuItem mnuNewGame = new JMenuItem("New Game"),
            mnuInstruction = new JMenuItem("Instructions"),
            mnuExit = new JMenuItem("Exit"),
            mnuAbout = new JMenuItem("About");

JButton btn1v1 = new JButton("Player vs Player"),
        btn1vCPU = new JButton("Player vs Computer"),
        btnQuit = new JButton("Quit"),
        btnSetName = new JButton("Set Player Names"),
        btnContinue = new JButton("Continue..."),
        btnTryAgain = new JButton("Try Again?");

JButton btnEmpty[] = new JButton[10];

JPanel pnlNewGame = new JPanel(),pnlMenu = new JPanel(),pnlMain = new JPanel(),pnlTop = new JPanel(),
        pnlBottom = new JPanel(),pnlQuitNTryAgain = new JPanel(),pnlPlayingField = new JPanel();

JLabel lblTitle = new JLabel("Welcome"),
        lblTurn = new JLabel(),
        lblStatus = new JLabel("", JLabel.CENTER),
        lblMode = new JLabel("", JLabel.LEFT);

JTextArea txtMessage = new JTextArea();
/*Horizontal Wins*/	/*Vertical Wins*/ /*Diagonal Wins*/
final int winCombo[][] = new int[][]{{1, 2, 3},{1, 4, 7},{1, 5, 9},{4, 5, 6},{2, 5, 8},{3, 5, 7},{7, 8, 9},{3, 6, 9}};

final int X =550,Y=350,mainColorR=190,mainColorG=50,mainColorB=50,btnColorR=70,btnColorG=70,btnColorB=70;

Color clrBtnWonColor = new Color(190,190,190);

int turn=1,player1Won=0,player2Won=0,wonNumber1=1,wonNumber2=1,wonNumber3=1,option;

boolean inGame=false,CPUGame=false,win=false;

String message,Player1="Player 1",Player2 ="Player 2",tempPlayer2 ="Player 2";
/**
 * Constructor
 */
public TicTacToe(){//Setting game properties and layout and sytle...
//Setting window properties:
window.setSize(X, Y);
window.setLocation(410,210);
window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
window.setLayout(new BorderLayout());

//Setting Menu, Main, Top, Bottom Panel Layout/Backgrounds
pnlMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
//Setting up Panels
pnlQuitNTryAgain.setLayout(new GridLayout(1,2,2,2));
pnlNewGame.setLayout(new GridLayout(4,1,0,5));
//Setting up the playing field
pnlPlayingField.setLayout(new GridLayout(3,3,2,2));

pnlNewGame.setBackground(new Color(mainColorR-50,mainColorG-50,mainColorB-50));
pnlMenu.setBackground(new Color((mainColorR-50),(mainColorG-50),(mainColorB-50)));
pnlMain.setBackground(new Color(mainColorR,mainColorG,mainColorB));
pnlTop.setBackground(new Color(mainColorR,mainColorG,mainColorB));
pnlBottom.setBackground(new Color(mainColorR,mainColorG,mainColorB));
txtMessage.setBackground(new Color(mainColorR-50,mainColorG-50,mainColorB-50));
pnlPlayingField.setBackground(Color.BLACK);
lblMode.setForeground(Color.WHITE);
txtMessage.setForeground(Color.WHITE);

pnlQuitNTryAgain.add(btnTryAgain);
pnlQuitNTryAgain.add(btnQuit);
//Adding items
mnuMain.add(mnuNewGame);
mnuMain.add(mnuInstruction);
mnuMain.add(mnuAbout);
mnuMain.add(mnuExit);
pnlNewGame.add(btnContinue);
pnlNewGame.add(btn1v1);
pnlNewGame.add(btn1vCPU);
pnlNewGame.add(btnSetName);
pnlMenu.add(lblMode);
pnlMenu.add(mnuMain);
pnlMain.add(lblTitle);
window.add(pnlMenu,BorderLayout.NORTH);
window.add(pnlMain,BorderLayout.CENTER);

btnTryAgain.setEnabled(false);
btnContinue.setEnabled(false);

txtMessage.setEditable(false);

//Adding Action Listener to all the Buttons and Menu Items
mnuNewGame.addActionListener(this);
mnuExit.addActionListener(this);
mnuInstruction.addActionListener(this);
mnuAbout.addActionListener(this);
btn1v1.addActionListener(this);
btn1vCPU.addActionListener(this);
btnQuit.addActionListener(this);
btnSetName.addActionListener(this);
btnContinue.addActionListener(this);
btnTryAgain.addActionListener(this);

for(int i=1; i<=9; i++)	{
    btnEmpty[i] = new JButton();
    btnEmpty[i].setBackground(new Color(btnColorR, btnColorG, btnColorB));
    btnEmpty[i].addActionListener(this);
    pnlPlayingField.add(btnEmpty[i]);
}
window.setVisible(true);
}

public static void main(String[] args)	{
        new TicTacToe();}
/**
 * Shows playing field
 */
public void showGame()	{	//	Shows the Playing Field
//	*IMPORTANT*- Does not start out brand new (meaning just shows what it had before)
clearPanelSouth();

pnlMain.setLayout(new BorderLayout());
pnlTop.setLayout(new BorderLayout());
pnlBottom.setLayout(new BorderLayout());

pnlTop.add(pnlPlayingField);
pnlBottom.add(lblTurn, BorderLayout.WEST);
pnlBottom.add(lblStatus, BorderLayout.CENTER);
pnlBottom.add(pnlQuitNTryAgain, BorderLayout.EAST);
pnlMain.add(pnlTop, BorderLayout.CENTER);
pnlMain.add(pnlBottom, BorderLayout.SOUTH);

//pnlPlayingField.requestFocus();

inGame = true;

checkTurn();

checkWinStatus();

}
/**
 * Sets all the game required variables to default and then shows the playing field.(Basically: Starts a new 1v1 Game)
 */
public void newGame()	{
btnEmpty[wonNumber1].setBackground(new Color(btnColorR, btnColorG, btnColorB));
btnEmpty[wonNumber2].setBackground(new Color(btnColorR, btnColorG, btnColorB));
btnEmpty[wonNumber3].setBackground(new Color(btnColorR, btnColorG, btnColorB));

for(int i=1; i<10; i++)	{
    btnEmpty[i].setText("");
    btnEmpty[i].setEnabled(true);}

turn = 1;
win = false;

showGame();
}
/**
 * Quits the Game
 */
public void quit()	{

inGame = false;
lblMode.setText("");
btnContinue.setEnabled(false);
clearPanelSouth();
setDefaultLayout();
pnlTop.add(pnlNewGame);
pnlMain.add(pnlTop);
}
/**
 * Checks if there are 3 symbols in a row vertically, diagonally, or horizontally.
 * then shows a message and disables buttons. If the game is over then it asks if you want to play again.
 * Checks if there are 3 symbols in a row vertically, diagonally, or horizontally.
 * Then shows a message and disables buttons. If the game is over then it asks if you want to play again.
 *
 * The way this checks the if someone won is:
 * First: it checks if the btnEmpty[x] is not equal to an empty string-	x being the array number
 * inside the multi-dimentional array winCombo[checks inside each of the 7 sets][the first number]
 * Second: it checks if btnEmpty[x] is equal to btnEmpty[y]- x being winCombo[each set][the first number]y
 * being winCombo[each set the same as x][the second number] (So basically checks if the first and
 * second number in each set is equal to each other)
 * Third: it checks if btnEmtpy[y] is eual to btnEmpty[z]- y being the same y as last time and z being
 * winCombo[each set as y][the third number]
 * Conclusion:	So basically it checks if it is equal to the btnEmpty is equal to each set of numbers
 */
public void checkWin(){
    for(int i=0; i<8; i++){
        if(!btnEmpty[winCombo[i][0]].getText().equals("") &&btnEmpty[winCombo[i][0]].getText().equals(btnEmpty[winCombo[i][1]].getText()) &&btnEmpty[winCombo[i][1]].getText().equals(btnEmpty[winCombo[i][2]].getText())){
            win = true;

            wonNumber1 = winCombo[i][0];
            wonNumber2 = winCombo[i][1];
            wonNumber3 = winCombo[i][2];

            btnEmpty[wonNumber1].setBackground(clrBtnWonColor);
            btnEmpty[wonNumber2].setBackground(clrBtnWonColor);
            btnEmpty[wonNumber3].setBackground(clrBtnWonColor);

            break;
        }
    }

    if(win ||(!win && turn>9)){
        if(win){
            if(btnEmpty[wonNumber1].getText().equals("X")){
                message = Player1 + " has won";
                player1Won++;
            }

            else{
                message = Player2 + " has won";
                player2Won++;
            }

        }
        else if(!win&&turn>9)
            message = "Both players have tied!\nBetter luck next time.";

        showMessage(message);

        for(int i=1; i<=9; i++)
            btnEmpty[i].setEnabled(false);

        btnTryAgain.setEnabled(true);

        checkWinStatus();
    }

    else
        checkTurn();
}
/**
 * Random no. Generator
 */
public void AI(){
    if(turn<10){
        turn++;
        Random r=new Random();
        int i=r.nextInt(10);
        if(i==0 || btnEmpty[i].getText()=="X" || btnEmpty[i].getText()=="O")
            Random1();
        else{
            btnEmpty[i].setText("O");
            btnEmpty[i].setEnabled(false);
        }
        checkWin();
    }
}
/**
 * To generate random no. if the board has something on the tile no. initially generated.
 */
public void Random1(){
    int random;
    if(turn <= 9){
        random = 0;

    while(random == 0)
      random = (int)(Math.random()*10);

    if("O".equals(btnEmpty[random].getText()) || "X".equals(btnEmpty[random].getText())|| random==0)
        Random1();

    else{
        btnEmpty[random].setText("O");
        btnEmpty[random].setEnabled(false);
    }
}
}
/**
 * Tells whose turn it is.
 */
public void checkTurn()	{
String whoTurn;
if(!(turn % 2 == 0))
    whoTurn = Player1 + " [X]";
else
    whoTurn = Player2 + " [O]";
lblTurn.setText("Turn: " + whoTurn);
}
/**
 * Ask the users for their names.
 */
public void askUserForPlayerNames()	{

String temp;
boolean tempIsValid = false;
temp = getInput("Enter player 1 name:", Player1);

if(temp == null){}

else if(temp.equals(""))

showMessage("Invalid Name!");

else if(temp.equals(Player2))	{
    option = askMessage("Player 1 name matches Player 2's\nDo you want to continue?", "Name Match", JOptionPane.YES_NO_OPTION);
    if(option == JOptionPane.YES_OPTION)
    tempIsValid = true;

}
else if(temp != null)
    tempIsValid = true;

if(tempIsValid)	{
    Player1 = temp;
    tempIsValid = false;
}

temp = getInput("Enter player 2 name:", Player2);

if(temp == null){}

else if(temp.equals(""))
    showMessage("Invalid Name!");

else if(temp.equals(Player1))	{
    option = askMessage("Player 2 name matches Player 1's\nDo you want to continue?", "Name Match", JOptionPane.YES_NO_OPTION);

    if(option == JOptionPane.YES_OPTION)
        tempIsValid = true;
}

else if(temp != null)
    tempIsValid = true;

if(tempIsValid)	{
    Player2 = temp;
    tempPlayer2 = temp;
    tempIsValid = false;
}

}
/**
 * Sets the layout.
 */
public void setDefaultLayout()	{
    pnlMain.setLayout(new GridLayout(2,1,2,5));
    pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
    pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
}
/**
 * Checks whether the game has ended or not.
 * If yes then tells Winner.
 */
public void checkWinStatus(){
    lblStatus.setText(Player1 + ": " + player1Won + " | " + Player2 + ": " + player2Won);
}
/**
 *
 * @param msg Asks for message.
 * @param tle JoptionPane
 * @return
 */
public int askMessage(String msg, String tle, int op)	{
    return JOptionPane.showConfirmDialog(null, msg, tle, op);
}
/**
 *
 * @param msg Gets the appropriate message.
 * @param setText Sets the correct message.
 * @return
 */
public String getInput(String msg, String setText){
    return JOptionPane.showInputDialog(null, msg, setText);
}
/**
 *
 * @param msg Shows the message.
 */
public void showMessage(String msg){
    JOptionPane.showMessageDialog(null, msg);
}
/**
 * Removes all the possible panels that pnlMain, pnlTop, pnlBottom could have.
 */
public void clearPanelSouth(){
    pnlMain.remove(lblTitle);
    pnlMain.remove(pnlTop);
    pnlMain.remove(pnlBottom);
    pnlTop.remove(pnlNewGame);
    pnlTop.remove(txtMessage);
    pnlTop.remove(pnlPlayingField);
    pnlBottom.remove(lblTurn);
    pnlBottom.remove(pnlQuitNTryAgain);
}
/**
 *
 * @param click Gets the click from user and does all the comparision.
 */
public void actionPerformed(ActionEvent click)	{
    Object source=click.getSource();
    for(int i=1;i<=9;i++){
        if(source==btnEmpty[i] && turn <10 && btnEmpty[i].getText()==""){
            if(turn%2==0)
                btnEmpty[i].setText("0");
            else
                btnEmpty[i].setText("X");

            btnEmpty[i].setEnabled(false);
            pnlPlayingField.requestFocus();
            turn++;

            checkWin();
        }
        if(CPUGame && win == false && turn%2==0)
            AI();
    }

    if(source==mnuNewGame || source == mnuInstruction || source == mnuAbout)	{
        clearPanelSouth();
        setDefaultLayout();
        if(source == mnuNewGame)//New Game
            pnlTop.add(pnlNewGame);

    else if(source == mnuInstruction || source == mnuAbout){
        if(source == mnuInstruction)// Instructions
            message = "Instructions:\n\n" +"Your goal is to be the first player to get 3 X's or O's in a\n" +"row. (horizontally, diagonally, or vertically)\n" +Player1 + ": X\n" +Player2 + ": O\n";
        else//About
            message = "About:\n\n" +"Title: Tic-Tac-Toe\n" +"Creator:Ajeet Singh and Anirudh Jain\n" +"Version: " + VERSION + "\n";

        txtMessage.setText(message);
        pnlTop.add(txtMessage);
    }
    pnlMain.add(pnlTop);
    }

    else if(source == btn1v1 || source == btn1vCPU){
        if(inGame){
            option = askMessage("If you start a new game," +"your current game will be lost..." + "\n" +"Are you sure you want to continue?","Quit Game?" ,JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION)
                inGame = false;
        }

        if(!inGame){
            btnContinue.setEnabled(true);
            if(source == btn1v1){// 1 v 1 Game
                Player2 = tempPlayer2;
                player1Won = 0;
                player2Won = 0;
                lblMode.setText("1 v 1");
                CPUGame=false;
                newGame();
            }
            else{// 1 v CPU Game
                Player2 = "Computer";
                player1Won = 0;
                player2Won = 0;
                lblMode.setText("1 v CPU");
                CPUGame=true;
                newGame();
            }
        }
    }

    else if(source == btnContinue){
        checkTurn();
        showGame();
    }

    else if(source == btnSetName)
        askUserForPlayerNames();

    else if(source == mnuExit){
        option = askMessage("Are you sure you want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
        if(option == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    else if(source == btnTryAgain){
        newGame();
        btnTryAgain.setEnabled(false);
    }

    else if(source == btnQuit)
        quit();

    pnlMain.setVisible(false);
    pnlMain.setVisible(true);
}
}
