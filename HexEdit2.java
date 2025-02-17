/*
  Hexadecimal File Editor #2
  Written by: Keith Fenske, http://kwfenske.github.io/
  Monday, 27 October 2008
  Java class name: HexEdit2
  Copyright (c) 2008 by Keith Fenske.  Apache License or GNU GPL.

  This is a Java 1.4 graphical (GUI) application to edit a file as a stream of
  hexadecimal digits, where each 8-bit byte is represented by two 4-bit
  "nibbles" (the hex digits).  No meaning is attached to the digits, and hence
  to the contents of the file, so this editing is very raw.  You can insert,
  delete, or replace digits or bytes, and you may view an approximate character
  equivalent of the digits in plain text (7-bit ASCII).  The primary purpose of
  a hex editor is to patch or correct specific locations within a file without
  affecting the rest of the file, something that most word processors can't do.
  A secondary purpose is to view the exact content of files.  (To generate a
  hex dump and save this dump to a file, see the DumpFile Java application.)

  The application window has two portions.  The top portion has standard Java
  buttons and options.  The bottom portion has a hexadecimal dump with three
  regions: file offsets on the left, bytes in hexadecimal in the middle, and
  ASCII text on the right.  The bottom looks like an old video terminal (quite
  deliberately), with a color scheme to match.  The Java buttons and options
  are straightforward after some experimentation.  Don't worry: no files are
  changed unless you click on the "Save File" button.  In fact, files aren't
  saved by default, so if you click on the Exit button without saving, any
  changes will be lost.  This may differ from what you expect with word
  processors or other types of editors.

  File offsets are 8-digit hexadecimal numbers to show you where the start of a
  row (dump line) is located from the beginning of the file.  Bytes are dumped
  (displayed) as two hexadecimal digits.  The first digit is the high-order
  digit in the byte; the second digit is the low-order digit.  All file bytes
  contain two digits, although during editing, you may see an odd number of
  nibbles in the file.  (A zero digit is appended if necessary when writing a
  file.)  Bytes are not grouped in any way, because different computers and
  programs have different ways of deciding which byte is the low-order byte in
  a word.  The text region on the right is only an approximation; most binary
  data is not text.  Bytes that are printable 7-bit ASCII characters are shown
  as text; anything else has a replacement character (".").  This does not mean
  that the program is limited to ASCII text.  You can copy and paste any text
  in the local system's default encoding, even if that encoding uses 8-bit
  bytes or multiple bytes.

  Other than the obvious scroll bar to move through the file, and the fun you
  can have resizing text by changing the number of bytes per line or the window
  size, your interaction is via the mouse and keyboard.  Most features are
  provided by both methods.

  The mouse can be used to position, select, scroll, or to open a context menu.
  To position the cursor (the location where data is inserted or deleted),
  click once with your primary mouse button, which is usually called a "left"
  click.  To select all data from a previous location to a new location, press
  and hold the "Shift" key on your keyboard and then left click on the new
  location.  (Release the Shift key when done.)  You may also select by
  clicking the mouse button, holding the button down, moving the mouse to a new
  location, and releasing the mouse button.  Selections must be entirely within
  the dump region or the text region.  If your mouse has a scroll wheel, you
  can rotate that wheel to move up or down in the file.  The final mouse
  feature is a context menu that will "pop up" if you click the right mouse
  button.  Not all computers have a right button, so any button other than the
  primary button will be accepted, and if your computer has only one mouse
  button, then hold down the "Control" key while clicking the primary mouse
  button.

  Regular text typed on the keyboard will be inserted into the file, or will
  replace a selection if a selection has been made.  For the dump region, only
  the decimal digits "0" to "9" are accepted, along with the uppercase
  hexadecimal digits "A" to "F" and the lowercase hexadecimal digits "a" to "f"
  (and a few punctuation characters are ignored).  For the text region, all
  printable characters are accepted, anything that Java can convert with the
  local system's default encoding.

  Keyboard Shortcuts
  ------------------
  There are numerous keyboard "shortcuts" or control codes.  In the following
  table, "Control-X" means to press and hold the "Control" key, then press and
  release the "X" key, then release the Control key.  Most of these key
  combinations are standard for editing applications, except that the unique
  nature of a hex dump changes the meaning of some keys.

  Alt         Invokes regular menus.  Officially, "Alt" stands for "Alternate"
              but nobody says it that way.

  Alt-M       Shows the "Edit Menu" (copy, paste, find, replace, select, etc).
              This is the same as the pop-up menu for right mouse clicks.

  Alt-O       Shows the "Open File" dialog box.

  Alt-S       Shows the "Save File" dialog box.

  Alt-X       Exits (closes) the program, no questions asked.  Closing the main
              window has the same effect.

  arrow keys  The left arrow moves the cursor one position to the left (one
              nibble for dumps, one byte for text).  The "Shift" key combined
              with the left arrow key (press and hold Shift key, press and
              release left arrow key, release Shift key) selects one position
              to the left, or expands the current selection by one position.
              The right arrow goes one position to the right, the up arrow goes
              one line/row up, and the down arrow goes one line/row down.

  Backspace   If there is a selection, then delete the selection.  Otherwise
              for hex dumps, delete the nibble (digit) before the cursor, and
              for ASCII text, delete the character (byte) before the cursor.
              This key may be "Bksp" on some keyboards, or just a left arrow
              (not to be confused with the real left arrow key).

  Control     Invokes keyboard shortcuts.  May be "Ctrl" on some keyboards.

  Control-A   Selects all data.

  Control-C   Copies selected data to the clipboard.  If the dump region is
              active, then text characters representing the hexadecimal digits
              are copied.  If the text region is active, the bytes are
              converted to text characters using the local system's default
              encoding.

  Control-F   Opens the "find" or "replace" dialog box.

  Control-G   Goes to a specific file byte offset in hexadecimal (dialog box).

  Control-N   Finds the next occurrence of the search string.

  Control-R   Replaces the current selection (if any) with the replacement
              string.

  Control-V   Pastes the clipboard as data.  Similar rules as Control-C.

  Control-X   Similar to Control-C except the selected data is deleted after it
              is copied to the clipboard.  Usually referred to as a "cut"
              operation.

  Control-Z   Reserved for an "undo" feature that hasn't been implemented yet.

  Delete      Same as Backspace, except that the deletion is forward: the
              nibble or byte following the cursor.  May be "Del" on some
              keyboards.

  End         Goes to the end of the current line/row, which (surprise!) turns
              out to be the beginning of the next line/row.  Control-End goes
              to the end of the file.  May be combined with the Shift key to
              select.

  Escape      Cancels any current selection.  May be "Esc" on some keyboards.

  F6          Makes the cursor active in the text region, while Shift-F6 makes
              the dump region active.

  Home        Goes to the beginning of the current line/row, or if already at
              the beginning, to the previous line/row.  Control-Home goes to
              the start of the file.  May be combined with the Shift key to
              select.

  Insert      Toggles between "insert" and "overwrite" mode.  Insert mode adds
              new nibbles/bytes at the cursor location (vertical line).
              Overwrite mode replaces nibbles/bytes at the cursor (box
              outline).  This key may be "Ins" on some keyboards.

  Page Down   Goes down as many lines/rows as there are in the display, less
              one.  May be combined with the Shift key to select.

  Page Up     Opposite of Page Down: goes up.  May be combined with Shift.

  Tab         Used by Java to traverse components (jump from button to button,
              etc).

  Apache License or GNU General Public License
  --------------------------------------------
  HexEdit2 is free software and has been released under the terms and
  conditions of the Apache License (version 2.0 or later) and/or the GNU
  General Public License (GPL, version 2 or later).  This program is
  distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY,
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
  PARTICULAR PURPOSE.  See the license(s) for more details.  You should have
  received a copy of the licenses along with this program.  If not, see the
  http://www.apache.org/licenses/ and http://www.gnu.org/licenses/ web pages.

  Graphical Versus Console Application
  ------------------------------------
  The Java command line may contain options for the insert/overwrite typing
  mode, the number of input bytes per dump line, and a file name.  See the "-?"
  option for a help summary:

      java  HexEdit2  -?

  The command line has more options than are visible in the graphical
  interface.  An option such as -u14 or -u16 is recommended because the default
  Java font is too small.

  Restrictions and Limitations
  ----------------------------
  There is an unusual circumstance where the mouse may point at the dump region
  but keyboard input is not accepted.  You can force this by clicking anywhere
  on the dump region, then without moving the mouse, open the "Edit Menu" with
  the Alt-M key combination, and cancel the menu with the Esc (escape) key.
  The buttons on top now have keyboard focus, even though the mouse is pointing
  at the dump on the bottom.  The dump region will regain focus as soon as you
  click or move the mouse.

  To handle insertions and deletions, the entire data file is buffered in
  memory as a split array of 4-bit "nibbles" (two data nibbles per 8-bit file
  byte).  To view a file, the Java heap size must be at least twice the size of
  the file.  To edit a file, it must be four times.  The default Java 1.4
  virtual machine on Windows will allow editing of files over 10 megabytes, and
  you may increase the maximum heap size with the "-Xmx" option on the Java
  command line.  This program is not recommended for files larger than 100
  megabytes unless you have a fast computer and disk drive.  The absolute
  maximum file size is one gigabyte, because nibbles in the file are counted
  with a signed 32-bit integer.

  Commentary: There Is A Reason
  -----------------------------
  There is a reason why most graphical applications use Java Swing components
  and let Swing take care of drawing, mouse clicks, etc: the interface is more
  consistent and easier to program.  This application is very close to what the
  JEditorPane and JTextPane classes handle well.  The only unusual features are
  the mirrored or shadowed cursors (one for the hex dump and one for the ASCII
  text) and navigation rules that skip file offsets, white space, and the text
  side markers.  Most of this could be done with JEditorPane/JTextPane, and
  could have more features than are available in this program (such as undo).

  The reason why programmers often avoid the obvious solution and write their
  own code is that it's more fun, you learn more, and you are better prepared
  in the future to decide which way to proceed.  Standard Java components don't
  always achieve the best results, and skill in writing your own "widgets"
  allows you more choice in customization.  Just remember: "The devil is in the
  details."  (Listed as an anonymous variation of "God is in the details" by
  the sixteenth edition of Bartlett's Familiar Quotations, edited by Justin
  Kaplan.)

  Suggestions for New Features
  ----------------------------
  (1) Should have an "undo" feature, and inexperienced users like confirmation
      for destructive changes (large deletions, an offer to save file on exit,
      etc).  KF, 2007-11-07.
  (2) I haven't found a way of allowing "ignore case" in text searches, without
      limiting text to 7-bit ASCII, or making assumptions about character set
      encodings.  KF, 2008-11-02.
  (3) A more general editor would show each byte in binary (8 digits), octal (3
      digits), decimal (3 digits), or hexadecimal (2 digits).  The numeric base
      would be a GUI option.  Data would be stored only as bytes (not nibbles).
      This would completely change the way internal data is accessed in this
      program, and is only suitable for a complete rewrite.  KF, 2008-11-04.
  (4) Would like a "replace all" feature with a way to cancel if this takes too
      long.  Jim H. via CNET Download.com, 2008-11-23.
  (5) The keyboard standard in Windows for plain left and right arrow keys (no
      shift key), when there is a selection, is that the left arrow cancels the
      selection and positions the cursor at what was previously the left side
      of the selection, independent of the selection direction or the current
      cursor location.  The right arrow key cancels the selection and moves to
      what was the right side.  This program cancels the selection and moves
      one unit from the current cursor location.  KF, 2009-11-30.
  (6) Pressing the Escape key should dismiss the "find" and "go to" dialog
      boxes.  KF, 2010-09-03.
  (7) A different structure is needed for very large files, which are slow to
      load into memory and may be many times bigger.  KF, 2010-09-04.
*/

import java.awt.*;                // older Java GUI support
import java.awt.datatransfer.*;   // clipboard
import java.awt.event.*;          // older Java GUI event support
import java.io.*;                 // standard I/O
import java.text.*;               // number formatting
import java.util.regex.*;         // regular expressions
import javax.swing.*;             // newer Java GUI support
import javax.swing.event.*;       // change listener

public class HexEdit2
{
  /* constants */

  static final int BUFFER_SIZE = 0x10000; // file buffer size in bytes (64 KB)
  static final int BYTE_MASK = 0x000000FF; // gets low-order byte from integer
  static final String COPYRIGHT_NOTICE =
    "Copyright (c) 2008 by Keith Fenske.  Apache License or GNU GPL.";
  static final int DEFAULT_DUMP = 8; // default input bytes per dump line
  static final int DEFAULT_HEIGHT = -1; // default window height in pixels
  static final int DEFAULT_LEFT = 50; // default window left position ("x")
  static final int DEFAULT_TOP = 50; // default window top position ("y")
  static final int DEFAULT_WIDTH = -1; // default window width in pixels
  static final String[] DUMP_WIDTHS = {"4", "8", "12", "16", "24", "32"};
                                  // number of input bytes per dump line
  static final String EMPTY_STATUS = " "; // message when no status to display
  static final char FIRST_CHAR = 0x20; // first printable ASCII character
  static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7',
    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}; // hexadecimal digits
  static final int HEX_IGNORE = -1; // special hex value for spaces, punctuation
  static final int HEX_INVALID = -2; // special hex value for illegal characters
  static final char LAST_CHAR = 0x7E; // last printable ASCII character
  static final char MARKER_CHAR = '|'; // left and right ASCII text markers
  static final String MARKER_STRING = Character.toString(MARKER_CHAR);
  static final int MIN_FRAME = 200; // minimum window height or width in pixels
  static final int NIBBLE_MASK = 0x0000000F; // low-order four bits from integer
  static final int NIBBLE_SHIFT = 4; // number of bits to shift for one nibble
  static final int OFFSET_DIGITS = 8; // hex digits in file offset (location)
  static final String PROGRAM_TITLE =
    "Hexadecimal File Editor - by: Keith Fenske";
  static final char REPLACE_CHAR = '.'; // replacement character for unprintable
  static final String SYSTEM_FONT = "Dialog"; // this font is always available
  static final Insets TEXT_MARGINS = new Insets(2, 3, 2, 3);
                                  // default top, left, bottom, right margins

  /* class variables */

  static Font buttonFont;         // font for buttons, labels, status, etc
  static String clipString;       // string that we put on the clipboard
  static int dumpWidth;           // number of input bytes per dump line
  static JComboBox dumpWidthDialog; // graphical option for <dumpWidth>
  static JButton exitButton;      // "Exit" button for ending this application
  static JFileChooser fileChooser; // asks for input and output file names
  static String fontName;         // font name for text in text area
  static JComboBox fontNameDialog; // graphical option for <fontName>
  static NumberFormat formatComma; // formats with commas (digit grouping)
  static JButton gotoCloseButton, gotoJumpButton; // buttons in "go to" dialog
  static JDialog gotoDialog;      // "Go To File Offset" dialog box
  static JTextField gotoOffsetText; // input text string with hex byte offset
  static JLabel gotoStatus;       // message string for "go to" information
  static JFrame mainFrame;        // this application's window for GUI
  static JButton menuButton;      // "Edit Menu" button
  static JMenuItem menuCopyCursor, menuCopyDump, menuCopyHex, menuCopyText,
    menuDelete, menuFind, menuGotoOffset, menuNext, menuPasteHex,
    menuPasteText, menuReplace, menuSelect; // menu items for <menuPopup>
  static JPopupMenu menuPopup;    // pop-up menu invoked by <menuButton>
  static boolean mswinFlag;       // true if running on Microsoft Windows
  static int nibbleCount;         // total number of 4-bit data nibbles
  static HexEdit2Data nibbleData; // two 4-bit nibbles for each 8-bit file byte
  static JButton openButton;      // "Open File" button to read data file
  static JCheckBox overDialog;    // graphical option for <overFlag>
  static boolean overFlag;        // true for overwrite mode, false for insert
  static JButton saveButton;      // "Save File" button to write new file
  static JCheckBox searchByteBound, searchIgnoreNulls; // search options
  static JButton searchCloseButton, searchFindButton, searchNextButton,
    searchReplaceButton;          // buttons in the search dialog
  static JDialog searchDialog;    // "Find or Replace" dialog box
  static JTextField searchFindText, searchReplaceText; // input text strings
  static JRadioButton searchIsHex, searchIsText; // search options
  static JLabel searchStatus;     // message string for search results
  static HexEdit2Text textPanel;  // displays hex dump and accepts user input
  static JScrollBar textScroll;   // vertical scroll bar beside <textPanel>
  static ActionListener userActions; // our shared action listener

/*
  main() method

  We run as a graphical application only.  Set the window layout and then let
  the graphical interface run the show.
*/
  public static void main(String[] args)
  {
    String fileName;              // first parameter that isn't an option
    int i;                        // index variable
    boolean maximizeFlag;         // true if we maximize our main window
    int windowHeight, windowLeft, windowTop, windowWidth;
                                  // position and size for <mainFrame>
    String word;                  // one parameter from command line

    /* Initialize global variables that may be affected by options on the
    command line. */

    buttonFont = null;            // by default, don't use customized font
    clipString = null;            // no string copied to clipboard yet
    dumpWidth = DEFAULT_DUMP;     // default input bytes per dump line
    fileName = "";                // first parameter is name of a file to open
    fontName = "Monospaced";      // default font name for text area
    gotoDialog = null;            // explicitly declare dialog as "not defined"
    maximizeFlag = false;         // true if we maximize our main window
    mswinFlag = System.getProperty("os.name").startsWith("Windows");
    nibbleCount = 0;              // total number of 4-bit data nibbles (none)
    nibbleData = new HexEdit2Data(0); // allocate empty data object for nibbles
    overFlag = false;             // by default, keyboard input has insert mode
    searchDialog = null;          // explicitly declare dialog as "not defined"
    windowHeight = DEFAULT_HEIGHT; // default window position and size
    windowLeft = DEFAULT_LEFT;
    windowTop = DEFAULT_TOP;
    windowWidth = DEFAULT_WIDTH;

    /* Initialize number formatting styles. */

    formatComma = NumberFormat.getInstance(); // current locale
    formatComma.setGroupingUsed(true); // use commas or digit groups

    /* We allow one argument (parameter) on the command line for the name of a
    file, and we do check for some options, but otherwise reject anything more
    on the command line. */

    for (i = 0; i < args.length; i ++)
    {
      word = args[i].toLowerCase(); // easier to process if consistent case
      if (word.length() == 0)
      {
        /* Ignore empty parameters, which are more common than you might think,
        when programs are being run from inside scripts (command files). */
      }

      else if (word.equals("?") || word.equals("-?") || word.equals("/?")
        || word.equals("-h") || (mswinFlag && word.equals("/h"))
        || word.equals("-help") || (mswinFlag && word.equals("/help")))
      {
        showHelp();               // show help summary
        System.exit(0);           // exit application after printing help
      }

      else if (word.equals("-d4") || (mswinFlag && word.equals("/d4")))
        dumpWidth = 4;            // user wants 4 input bytes per dump line
      else if (word.equals("-d8") || (mswinFlag && word.equals("/d8")))
        dumpWidth = 8;
      else if (word.equals("-d12") || (mswinFlag && word.equals("/d12")))
        dumpWidth = 12;
      else if (word.equals("-d16") || (mswinFlag && word.equals("/d16")))
        dumpWidth = 16;
      else if (word.equals("-d24") || (mswinFlag && word.equals("/d24")))
        dumpWidth = 24;
      else if (word.equals("-d32") || (mswinFlag && word.equals("/d32")))
        dumpWidth = 32;

      else if (word.equals("-ins") || (mswinFlag && word.equals("/ins")))
        overFlag = false;         // input starts in insert mode

      else if (word.equals("-over") || (mswinFlag && word.equals("/over")))
        overFlag = true;          // input starts in overwrite mode

      else if (word.startsWith("-u") || (mswinFlag && word.startsWith("/u")))
      {
        /* This option is followed by a font point size that will be used for
        buttons, dialogs, labels, etc. */

        int size = -1;            // default value for font point size
        try                       // try to parse remainder as unsigned integer
        {
          size = Integer.parseInt(word.substring(2));
        }
        catch (NumberFormatException nfe) // if not a number or bad syntax
        {
          size = -1;              // set result to an illegal value
        }
        if ((size < 10) || (size > 99))
        {
          System.err.println("Dialog font size must be from 10 to 99: "
            + args[i]);           // notify user of our arbitrary limits
          showHelp();             // show help summary
          System.exit(-1);        // exit application after printing help
        }
        buttonFont = new Font(SYSTEM_FONT, Font.PLAIN, size); // for big sizes
//      buttonFont = new Font(SYSTEM_FONT, Font.BOLD, size); // for small sizes
      }

      else if (word.startsWith("-w") || (mswinFlag && word.startsWith("/w")))
      {
        /* This option is followed by a list of four numbers for the initial
        window position and size.  All values are accepted, but small heights
        or widths will later force the minimum packed size for the layout. */

        Pattern pattern = Pattern.compile(
          "\\s*\\(\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*\\)\\s*");
        Matcher matcher = pattern.matcher(word.substring(2)); // parse option
        if (matcher.matches())    // if option has proper syntax
        {
          windowLeft = Integer.parseInt(matcher.group(1));
          windowTop = Integer.parseInt(matcher.group(2));
          windowWidth = Integer.parseInt(matcher.group(3));
          windowHeight = Integer.parseInt(matcher.group(4));
        }
        else                      // bad syntax or too many digits
        {
          System.err.println("Invalid window position or size: " + args[i]);
          showHelp();             // show help summary
          System.exit(-1);        // exit application after printing help
        }
      }

      else if (word.equals("-x") || (mswinFlag && word.equals("/x")))
        maximizeFlag = true;      // true if we maximize our main window

      else if (word.startsWith("-") || (mswinFlag && word.startsWith("/")))
      {
        System.err.println("Option not recognized: " + args[i]);
        showHelp();               // show help summary
        System.exit(-1);          // exit application after printing help
      }

      else
      {
        /* Parameter does not look like an option.  Assume that this is a file
        name. */

        if (fileName.length() == 0) // do we already have a file name?
          fileName = args[i];     // no, use original parameter (not lowercase)
        else
        {
          System.err.println("Only one file name accepted: " + args[i]);
          showHelp();             // show help summary
          System.exit(-1);        // exit application after printing help
        }
      }
    }

    /* Open the graphical user interface (GUI).  The standard Java style is the
    most reliable, but you can switch to something closer to the local system,
    if you want. */

//  try
//  {
//    UIManager.setLookAndFeel(
//      UIManager.getCrossPlatformLookAndFeelClassName());
////    UIManager.getSystemLookAndFeelClassName());
//  }
//  catch (Exception ulafe)
//  {
//    System.err.println("Unsupported Java look-and-feel: " + ulafe);
//  }

    /* Initialize shared graphical objects. */

    fileChooser = new JFileChooser(); // create our shared file chooser
    fileChooser.resetChoosableFileFilters(); // remove any existing filters
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setMultiSelectionEnabled(false); // allow only one file
    userActions = new HexEdit2User(); // create our shared action listener

    /* Create the graphical interface as a series of little panels inside
    bigger panels.  The intermediate panel names are of no lasting importance
    and hence are only numbered (panel1, panel2, etc). */

    /* Create a vertical box to stack buttons and options. */

    JPanel panel1 = new JPanel();
    panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
    panel1.add(Box.createVerticalStrut(15)); // extra space at panel top

    /* Create a horizontal panel for the action buttons. */

    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));

    openButton = new JButton("Open File...");
    openButton.addActionListener(userActions);
    if (buttonFont != null) openButton.setFont(buttonFont);
    openButton.setMnemonic(KeyEvent.VK_O);
    openButton.setToolTipText("Read data bytes from a file.");
    panel2.add(openButton);

    saveButton = new JButton("Save File...");
    saveButton.addActionListener(userActions);
    if (buttonFont != null) saveButton.setFont(buttonFont);
    saveButton.setMnemonic(KeyEvent.VK_S);
    saveButton.setToolTipText("Write data bytes to a file.");
    panel2.add(saveButton);

    menuButton = new JButton("Edit Menu");
    menuButton.addActionListener(userActions);
    if (buttonFont != null) menuButton.setFont(buttonFont);
    menuButton.setMnemonic(KeyEvent.VK_M);
    menuButton.setToolTipText("Copy, delete, find, paste, replace, etc.");
    panel2.add(menuButton);

    exitButton = new JButton("Exit");
    exitButton.addActionListener(userActions);
    if (buttonFont != null) exitButton.setFont(buttonFont);
    exitButton.setMnemonic(KeyEvent.VK_X);
    exitButton.setToolTipText("Close this program.");
    panel2.add(exitButton);

    panel1.add(panel2);
    panel1.add(Box.createVerticalStrut(13));

    /* These are the individual menu items for <menuPopup> which is invoked by
    <menuButton> or a right mouse click.  They are assembled into a real menu
    later by the showEditMenu() method. */

    menuCopyCursor = new JMenuItem("Copy Cursor Offset");
    menuCopyCursor.addActionListener(userActions);
    if (buttonFont != null) menuCopyCursor.setFont(buttonFont);

    menuCopyDump = new JMenuItem("Copy Dump");
    menuCopyDump.addActionListener(userActions);
    if (buttonFont != null) menuCopyDump.setFont(buttonFont);

    menuCopyHex = new JMenuItem("Copy Hex");
    menuCopyHex.addActionListener(userActions);
    if (buttonFont != null) menuCopyHex.setFont(buttonFont);

    menuCopyText = new JMenuItem("Copy Text");
    menuCopyText.addActionListener(userActions);
    if (buttonFont != null) menuCopyText.setFont(buttonFont);

    menuDelete = new JMenuItem("Delete");
    menuDelete.addActionListener(userActions);
    if (buttonFont != null) menuDelete.setFont(buttonFont);
    menuDelete.setMnemonic(KeyEvent.VK_D);

    menuFind = new JMenuItem("Find...");
    menuFind.addActionListener(userActions);
    if (buttonFont != null) menuFind.setFont(buttonFont);
    menuFind.setMnemonic(KeyEvent.VK_F);

    menuGotoOffset = new JMenuItem("Go To File Offset...");
    menuGotoOffset.addActionListener(userActions);
    if (buttonFont != null) menuGotoOffset.setFont(buttonFont);
    menuGotoOffset.setMnemonic(KeyEvent.VK_G);

    menuNext = new JMenuItem("Find Next");
    menuNext.addActionListener(userActions);
    if (buttonFont != null) menuNext.setFont(buttonFont);
    menuNext.setMnemonic(KeyEvent.VK_N);

    menuPasteHex = new JMenuItem("Paste Hex");
    menuPasteHex.addActionListener(userActions);
    if (buttonFont != null) menuPasteHex.setFont(buttonFont);

    menuPasteText = new JMenuItem("Paste Text");
    menuPasteText.addActionListener(userActions);
    if (buttonFont != null) menuPasteText.setFont(buttonFont);

    menuReplace = new JMenuItem("Replace");
    menuReplace.addActionListener(userActions);
    if (buttonFont != null) menuReplace.setFont(buttonFont);
    menuReplace.setMnemonic(KeyEvent.VK_R);

    menuSelect = new JMenuItem("Select All");
    menuSelect.addActionListener(userActions);
    if (buttonFont != null) menuSelect.setFont(buttonFont);
    menuSelect.setMnemonic(KeyEvent.VK_A);

    /* Create a horizontal panel for the options. */

    JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

    fontNameDialog = new JComboBox(GraphicsEnvironment
      .getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    fontNameDialog.setEditable(false); // user must select one of our choices
    if (buttonFont != null) fontNameDialog.setFont(buttonFont);
    fontNameDialog.setSelectedItem(fontName); // select default font name
    fontNameDialog.setToolTipText("Font name for display text.");
    fontNameDialog.addActionListener(userActions);
                                  // do last so this doesn't fire early
    panel3.add(fontNameDialog);

    panel3.add(Box.createHorizontalStrut(30));

    dumpWidthDialog = new JComboBox(DUMP_WIDTHS);
    dumpWidthDialog.setEditable(false); // user must select one of our choices
    if (buttonFont != null) dumpWidthDialog.setFont(buttonFont);
    dumpWidthDialog.setSelectedItem(String.valueOf(dumpWidth));
                                  // selected item is our default size
    dumpWidthDialog.setToolTipText("Number of input bytes per dump line.");
    dumpWidthDialog.addActionListener(userActions);
                                  // do last so this doesn't fire early
    panel3.add(dumpWidthDialog);
    JLabel label1 = new JLabel("bytes per line");
    if (buttonFont != null) label1.setFont(buttonFont);
    panel3.add(label1);

    panel3.add(Box.createHorizontalStrut(20));

    overDialog = new JCheckBox("overwrite mode", overFlag);
    overDialog.addActionListener(userActions);
    if (buttonFont != null) overDialog.setFont(buttonFont);
    overDialog.setToolTipText("Select for overwrite, clear for insert mode.");
    panel3.add(overDialog);

    panel1.add(panel3);
    panel1.add(Box.createVerticalStrut(7));

    /* Put above boxed options in a panel that is centered horizontally. */

    JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    panel4.add(panel1);

    /* Create a text area to display the hex dump and to accept user input.  To
    the right of that will be a vertical scroll bar that we control. */

    textPanel = new HexEdit2Text(); // create display as special JPanel
    textPanel.setFocusable(true); // allow keyboard focus for dump display
    textPanel.setPreferredSize(new Dimension(panel4.getPreferredSize().width,
      (4 * panel4.getPreferredSize().height))); // 4 times button/option height

    textScroll = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, 1);
    textScroll.addMouseWheelListener((MouseWheelListener) textPanel);
    textScroll.setEnabled(true);  // scroll bar always present, always enabled
    textScroll.setFocusable(true); // allow keyboard focus for scroll bar
    textScroll.getModel().addChangeListener((ChangeListener) textPanel);

    /* Create the main window frame for this application.  Stack buttons and
    options on top of the output text area.  Keep the display text in the
    center so that it expands horizontally and vertically. */

    mainFrame = new JFrame(PROGRAM_TITLE);
    Container panel6 = mainFrame.getContentPane(); // where content meets frame
    panel6.setLayout(new BorderLayout(5, 5));
    panel6.add(panel4, BorderLayout.NORTH); // buttons and options
    panel6.add(textPanel, BorderLayout.CENTER); // our panel for dump display
    panel6.add(textScroll, BorderLayout.EAST); // scroll bar for dump display

    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLocation(windowLeft, windowTop); // normal top-left corner
    if ((windowHeight < MIN_FRAME) || (windowWidth < MIN_FRAME))
      mainFrame.pack();           // do component layout with minimum size
    else                          // the user has given us a window size
      mainFrame.setSize(windowWidth, windowHeight); // size of normal window
    if (maximizeFlag) mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainFrame.validate();         // recheck application window layout
    mainFrame.setVisible(true);   // and then show application window

    /* If a file name was given on the command line, then open that file as our
    starting data.  Otherwise, use a text string with copyright information. */

    if (fileName.length() > 0)    // was there a file name on the command line?
      openFile(new File(fileName)); // yes, read data bytes from that file
    else                          // no file name given
    {
      byte[] array = COPYRIGHT_NOTICE.getBytes(); // use copyright string
      nibbleData = new HexEdit2Data(array.length * 2);
                                  // allocate data object to hold nibbles
      for (i = 0; i < array.length; i ++) // do all bytes in copyright string
      {
        nibbleData.append((array[i] >> NIBBLE_SHIFT) & NIBBLE_MASK);
                                  // high-order nibble in byte
        nibbleData.append(array[i] & NIBBLE_MASK);
                                  // low-order nibble in byte
      }
      refreshDataSize();          // set to correct number of data nibbles
    }
    textPanel.beginFile();        // display file from the beginning

    /* Let the graphical interface run the application now. */

//  mainFrame.setVisible(true);   // and then show application window

  } // end of main() method

// ------------------------------------------------------------------------- //

/*
  canWriteFile() method

  The caller gives us a Java File object.  We return true if it seems safe to
  write to this file.  That is, if the file doesn't exist and we can create a
  new file, or if the file exists and the user gives permission to replace it.
*/
  static boolean canWriteFile(File givenFile)
  {
    boolean result;               // status flag that we return to the caller

    if (givenFile.isDirectory())  // can't write to folders/directories
    {
      JOptionPane.showMessageDialog(mainFrame, (givenFile.getName()
        + " is a directory or folder.\nPlease select a normal file."));
      result = false;             // don't try to open this "file" for writing
    }
    else if (givenFile.isHidden()) // won't write to hidden (protected) files
    {
      JOptionPane.showMessageDialog(mainFrame, (givenFile.getName()
        + " is a hidden or protected file.\nPlease select a normal file."));
      result = false;
    }
    else if (givenFile.isFile() == false) // are we creating a new file?
    {
      result = true;              // assume we can create new file by this name
    }
    else if (givenFile.canWrite()) // file exists, but can we write to it?
    {
      result = (JOptionPane.showConfirmDialog(mainFrame, (givenFile.getName()
        + " already exists.\nDo you want to replace this with a new file?"))
        == JOptionPane.YES_OPTION);
    }
    else                          // if we can't write to an existing file
    {
      JOptionPane.showMessageDialog(mainFrame, (givenFile.getName()
        + " is locked or write protected.\nCan't write to this file."));
      result = false;
    }
    return(result);               // give caller our best guess about writing

  } // end of canWriteFile() method


/*
  charHexValue() method

  Return the integer value from 0 to 15 for one character that should be a
  hexadecimal digit.  A negative result means that the character is not a hex
  digit; see the HEX_IGNORE and HEX_INVALID constants.
*/
  static int charHexValue(char ch)
  {
    int result;                   // integer value of hexadecimal digit

    if ((ch >= '0') && (ch <= '9')) // is this a decimal digit?
      result = ch - '0';
    else if ((ch >= 'A') && (ch <= 'F')) // uppercase hexadecimal digit?
      result = ch - 'A' + 10;
    else if ((ch >= 'a') && (ch <= 'f')) // lowercase hexadecimal digit?
      result = ch - 'a' + 10;
    else if ((ch == ',') || (ch == '.') || (ch == ':')) // punctuation?
      result = HEX_IGNORE;
    else if (Character.isWhitespace(ch)) // blanks, spaces, tabs, newlines?
      result = HEX_IGNORE;
    else                          // illegal character
      result = HEX_INVALID;

    return(result);               // give caller whatever we could find

  } // end of charHexValue() method


/*
  copyCursor() method

  Copy the current cursor nibble index to the clipboard as a hexadecimal string
  with the equivalent file byte offset.  As always, remember that there are two
  nibbles per byte!  This feature is normally only useful when combined with
  the "Go To File Offset" dialog box.
*/
  static void copyCursor()
  {
    String text;                  // text string in middle of hex conversion

    text = "00000000" + Integer.toHexString(textPanel.cursorDot / 2)
      .toUpperCase();             // current cursor nibble as hex byte offset
    setClipboard(text.substring(text.length() - OFFSET_DIGITS));
  }


/*
  copyDump() method

  Copy the currently selected portion of the dump to the clipboard as a string
  with lines delimited by newline characters.  While from the user's point of
  view we are copying text that is already on the screen, we actually have to
  rebuild the dump for each line that has some part selected.
*/
  static void copyDump()
  {
    int beginIndex, endIndex, thisIndex; // nibble index variables
    int byteValue;                // byte value combined from two nibbles
    int i;                        // index variable
    StringBuffer lineBuffer;      // where we create each dump line
    int lineLength;               // total number of characters each dump line
    int lineNibbles;              // maximum number of hex digits per dump line
    int lineUsed;                 // number of nibbles placed on this line
    int nextHex, nextText;        // indexes for placing next nibble, byte
    StringBuffer result;          // string created from selected dump lines
    int shiftedOffset;            // copy of file offset that we manipulate

    beginIndex = Math.min(textPanel.cursorDot, textPanel.cursorMark);
    endIndex = Math.max(textPanel.cursorDot, textPanel.cursorMark);
    if (beginIndex < endIndex)    // only if there is a selection
    {
      lineLength = OFFSET_DIGITS + (4 * dumpWidth) + 5;
                                  // number of printable chars each dump line
      lineBuffer = new StringBuffer(lineLength + 1);
                                  // allocate line buffer, ends with newline
      lineBuffer.setLength(lineLength + 1); // force the correct buffer length
      lineBuffer.setCharAt(lineLength, '\n'); // put newline that never changes
      lineNibbles = 2 * dumpWidth; // maximum number of nibbles per dump line
      lineUsed = -1;              // force initialization of a new dump line
      nextHex = nextText = -1;    // just to keep compiler happy
      result = new StringBuffer(); // start with an empty string buffer
      for (thisIndex = beginIndex; thisIndex < endIndex; thisIndex ++)
      {
        /* If the current dump line is full, then copy it to our result. */

        if (lineUsed >= lineNibbles) // is the current dump line full?
        {
          result.append(lineBuffer); // append each formatted line to result
          lineUsed = -1;          // force initialization of a new dump line
        }

        /* If necessary, initialize the dump line with the file offset. */

        if (lineUsed < 0)         // should we start a new dump line?
        {
          for (i = 0; i < lineLength; i ++) // clear entire line to spaces
            lineBuffer.setCharAt(i, ' ');
          lineBuffer.setCharAt((lineLength - dumpWidth - 2), MARKER_CHAR);
                                  // insert left marker for ASCII text
          lineBuffer.setCharAt((lineLength - 1), MARKER_CHAR); // right marker
          shiftedOffset = (thisIndex / lineNibbles) * dumpWidth;
          for (i = (OFFSET_DIGITS - 1); i >= 0; i --)
                                  // extract digits starting with low-order
          {
            lineBuffer.setCharAt(i, HEX_DIGITS[shiftedOffset & NIBBLE_MASK]);
                                  // convert nibble to hex text digit
            shiftedOffset = shiftedOffset >> NIBBLE_SHIFT;
                                  // shift down next higher-order nibble
          }
          lineUsed = thisIndex % lineNibbles; // ignore leading unused digits
          nextHex = lineUsed + (lineUsed / 2) + OFFSET_DIGITS + 2;
                                  // where next hex digit goes
          nextText = lineLength - dumpWidth - 1 + (lineUsed / 2);
                                  // where next ASCII text goes
        }

        /* Place the hexadecimal digit for this nibble. */

        lineBuffer.setCharAt((nextHex ++),
          HEX_DIGITS[nibbleData.get(thisIndex)]);
        nextHex += thisIndex % 2; // insert extra space after second nibble
        lineUsed ++;              // one more nibble placed on this dump line

        /* Place the ASCII text for a whole byte (two nibbles). */

        if ((thisIndex % 2) == 1) // for second nibble, construct full byte
        {
          if ((thisIndex - 1) < beginIndex) // is first nibble in selection?
            byteValue = REPLACE_CHAR; // incomplete byte means unprintable
          else                    // we have first and second nibble
          {
            byteValue = (nibbleData.get(thisIndex - 1) << NIBBLE_SHIFT)
              | nibbleData.get(thisIndex); // construct byte from two nibbles
            if ((byteValue < FIRST_CHAR) || (byteValue > LAST_CHAR))
              byteValue = REPLACE_CHAR; // replace unprintable character
          }
          lineBuffer.setCharAt((nextText ++), (char) byteValue); // show text
        }
        else if ((thisIndex + 1) >= endIndex) // first nibble, but alone?
          lineBuffer.setCharAt((nextText ++), REPLACE_CHAR); // unprintable
      }

      /* There is always a partial line after the loop completes. */

      result.append(lineBuffer);  // append last formatted line to result
      setClipboard(result.toString()); // and buffer becomes clipboard string
    }
  } // end of copyDump() method


/*
  copyHex() method

  Copy the currently selected portion of our data to the clipboard as
  hexadecimal digits (that is, a string representing the hex values).
*/
  static void copyHex()
  {
    int beginIndex, endIndex, thisIndex; // nibble index variables
    StringBuffer result;          // string created from selected hex digits

    beginIndex = Math.min(textPanel.cursorDot, textPanel.cursorMark);
    endIndex = Math.max(textPanel.cursorDot, textPanel.cursorMark);
    if (beginIndex < endIndex)    // only if there is a selection
    {
      result = new StringBuffer(); // start with an empty string buffer
      for (thisIndex = beginIndex; thisIndex < endIndex; thisIndex ++)
        result.append(HEX_DIGITS[nibbleData.get(thisIndex)]);
                                  // convert binary nibble to hex character
      setClipboard(result.toString()); // and buffer becomes clipboard string
    }
  } // end of copyHex() method


/*
  copyText() method

  Copy the currently selected portion of our nibble data to the clipboard as
  text (that is, a string).  To be consistent with pasteText(), first we create
  a byte array, then we convert the bytes to a string using the local system's
  default encoding.  No attempt is made to align the selected nibbles on a byte
  boundary; this may produce unexpected results.
*/
  static void copyText()
  {
    byte[] array;                 // array of bytes obtained from a string
    int beginIndex, endIndex, thisIndex; // nibble index variables
    int byteValue;                // for creating shifted sum of two nibbles
    char ch;                      // one character from decoded string
    boolean crFound;              // true if previous char was carriage return
    String decoded;               // intermediate copy of decoded string
    int i;                        // index variable
    int length;                   // length of decoded string in characters
    StringBuffer result;          // string created from selected hex digits

    beginIndex = Math.min(textPanel.cursorDot, textPanel.cursorMark);
    endIndex = Math.max(textPanel.cursorDot, textPanel.cursorMark);
    if (beginIndex < endIndex)    // only if there is a selection
    {
      array = new byte[(endIndex - beginIndex + 1) / 2]; // round up byte size
      thisIndex = beginIndex;     // first nibble is high-order of first byte
      for (i = 0; i < array.length; i ++) // create each byte from two nibbles
      {
        byteValue = nibbleData.get(thisIndex ++) << NIBBLE_SHIFT;
                                  // can always get high-order nibble
        if (thisIndex < endIndex) // there may not be a low-order nibble
          byteValue |= nibbleData.get(thisIndex ++);
        array[i] = (byte) byteValue; // copy summed nibbles to byte array
      }

      /* The Windows clipboard (and possibly others) will truncate a string if
      certain characters such as nulls are found.  Recognize a single carriage
      return (CR), a single line feed (LF), or a CR/LF pair as being equivalent
      to a standard newline character (NL).  Note: the binary value of LF is
      the same as NL. */

      crFound = false;            // previous character was not carriage return
      decoded = new String(array); // decode byte array, default character set
      length = decoded.length();  // get number of characters in decoded string
      result = new StringBuffer(length); // assume maximum capacity for buffer
      for (i = 0; i < length; i ++) // check all characters in decoded string
      {
        ch = decoded.charAt(i);   // get one character from decoded string
        if (crFound && (ch != '\n')) // carriage return without line feed?
          result.append('\n');    // yes, previous CR becomes newline character
        crFound = false;          // previous character is no longer important
        if (ch == '\n')           // accept newline character (DOS LF, UNIX NL)
          result.append(ch);
        else if (ch == '\r')      // delay action for carriage return (CR)
          crFound = true;
        else if (ch == '\t')      // accept horizontal tab character (HT)
          result.append(ch);
        else if ((ch <= 0x1F) || ((ch >= 0x7F) && (ch <= 0x9F)))
          { /* ignore all other control codes */ }
        else
          result.append(ch);      // otherwise, this character is acceptable
      }
      if (crFound)                // if last character was a carriage return
        result.append('\n');      // then end copied string with a newline
      setClipboard(result.toString()); // and buffer becomes clipboard string
    }
  } // end of copyText() method


/*
  deleteSelected() method

  Delete (remove) hex nibbles or text bytes as selected by the user.  From our
  point of view, we don't need to know what was selected (hex or text), since
  we always delete all nibbles from the start of the selection up to but not
  including the end of the selection.
*/
  static void deleteSelected()
  {
    int beginIndex, endIndex, thisIndex; // nibble index variables

    beginIndex = Math.min(textPanel.cursorDot, textPanel.cursorMark);
    endIndex = Math.max(textPanel.cursorDot, textPanel.cursorMark);
    if (beginIndex < endIndex)    // only if there is a selection
    {
      for (thisIndex = beginIndex; thisIndex < endIndex; thisIndex ++)
        nibbleData.delete(beginIndex); // delete shuffles, always <beginIndex>
      textPanel.cursorDot = textPanel.cursorMark = beginIndex;
                                  // selection is gone, reset cursor
      textPanel.limitCursorRange(); // refresh data size, enforce cursor range
      textPanel.makeVisible(textPanel.cursorDot);
                                  // make sure that user can see cursor
      textPanel.adjustScrollBar(); // adjust scroll bar to match new position
      textPanel.repaint();        // redraw text display as necessary
    }
  } // end of deleteSelected() method


/*
  getClipboard() method

  Get the contents of the clipboard as a string.  For any errors, return an
  empty string.
*/
  static String getClipboard()
  {
    String result;                // our result as a string

    try                           // clipboard may not be available
    {
      result = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
        .getContents(null).getTransferData(DataFlavor.stringFlavor);
    }
    catch (IllegalStateException ise) { result = ""; }
    catch (IOException ioe) { result = ""; }
    catch (UnsupportedFlavorException ufe) { result = ""; }
    return(result);               // give caller whatever we could find

  } // end of getClipboard() method


/*
  gotoFileOffset() method

  Take the user's input from the "Go To File Offset" dialog box and try to
  position the cursor at that byte offset from the beginning of the file.  We
  know the dialog box exists, because we can only get here from buttons and
  fields inside the dialog box.
*/
  static void gotoFileOffset()
  {
    char ch;                      // one character from input string
    int digits;                   // number of valid hex digits found
    int hexValue;                 // integer value of one hexadecimal digit
    int i;                        // index variable
    String input;                 // offset (input) string typed by user
    int length;                   // size of input string in characters
    long offset;                  // user's offset converted to binary

    /* Try to convert the user's input from a hexadecimal string to a binary
    integer value. */

    refreshDataSize();            // set to correct number of data nibbles
    digits = 0;                   // no valid hex digits found yet
    input = gotoOffsetText.getText(); // get user's string from dialog box
    length = input.length();      // get size of input string in characters
    offset = 0;                   // start offset from zero as we add digits
    for (i = 0; i < length; i ++) // do all characters in the string
    {
      ch = input.charAt(i);       // get one character from input string
      hexValue = charHexValue(ch); // convert character to value of hex digit
      if (offset > nibbleCount)   // crude (very crude) limit on upper range
      {
        digits = -1;              // our way of saying that hex string is bad
        break;                    // exit early from <for> loop
      }
      else if (hexValue >= 0)     // was it a valid hexadecimal digit?
      {
        digits ++;                // one more hexadecimal digit accepted
        offset = (offset << NIBBLE_SHIFT) | hexValue; // append digit to offset
      }
      else if (hexValue == HEX_IGNORE) // ignore spaces and punctuation?
        { /* do nothing */ }
      else                        // illegal character
      {
        digits = -1;              // another way of saying that input is bad
        break;                    // exit early from <for> loop
      }
    }

    /* Check that the resulting binary value is within actual file range. */

    if ((digits < 1) || (offset > (nibbleCount / 2)))
    {
      showGotoRange(nibbleCount / 2); // repeat message about range allowed
      return;                     // can't jump to an imaginary file offset!
    }

    /* Position the display to the correct file (and dump panel) offset, about
    1/3 of the way down from the top, if possible. */

    gotoStatus.setText(EMPTY_STATUS); // less confusing if clear status message
    mainFrame.setVisible(true);   // bring main frame in front of dialog box
    textPanel.cursorDot = textPanel.cursorMark = (int) (offset * 2);
                                  // position cursor at user's exact offset
    textPanel.limitCursorRange(); // refresh data size, enforce cursor range
    textPanel.panelOffset = ((int) offset) - (textPanel.panelDumpWidth
      * (textPanel.panelRows / 3)); // approximate starting panel offset
    textPanel.adjustScrollBar();  // clean up offset and adjust scroll bars
    textPanel.repaint();          // redraw text display as necessary

  } // end of gotoFileOffset() method


/*
  memoryError() method

  Produce a dialog box with a message when there is insufficient memory to
  complete an operation.
*/
  static void memoryError(String text)
  {
    JOptionPane.showMessageDialog(mainFrame,
      ("Not enough memory available for " + text + " operation."));
  }


/*
  openFile() method

  Ask the user for an input file name and copy the contents of that file to our
  nibble data.
*/
  static void openFile(File givenFile)
  {
    byte[] buffer;                // input buffer (faster than byte-by-byte)
    int i;                        // index variable
    File inputFile;               // user's selected input file
    long inputSize;               // total size of input file in bytes
    FileInputStream inputStream;  // input file stream
    int length;                   // actual number of bytes read

    /* Clear the nibble counter so that the data looks empty, until after we
    finish opening a file.  This prevents the text display from throwing an
    exception while painting data that isn't there yet.  The nibble counter
    will be reset properly with a call to refreshDataSize(). */

    nibbleCount = 0;              // make data look empty, without losing data

    /* Ask the user for an input file name, if we weren't already given a file
    by our caller. */

    if (givenFile == null)        // should we ask the user for a file name?
    {
      fileChooser.setDialogTitle("Open File...");
      if (fileChooser.showOpenDialog(mainFrame) != JFileChooser.APPROVE_OPTION)
      {
        refreshDataSize();        // bring back previous nibble data and size
        return;                   // user cancelled file selection dialog box
      }
      inputFile = fileChooser.getSelectedFile(); // get user's input file
    }
    else                          // caller gave us file name (may not exist)
    {
      fileChooser.setCurrentDirectory(givenFile); // remember this directory
      fileChooser.setSelectedFile(givenFile); // remember caller's file name
      inputFile = givenFile;      // use caller's file name without asking
    }

    /* Warn the user if the file is larger than what we are able to handle. */

    inputSize = inputFile.length(); // get total number of bytes for input file
    if (inputSize > 0x3FFF0000L)  // just short of one gigabyte
    {
      JOptionPane.showMessageDialog(mainFrame,
        ("This program can't open files larger than one gigabyte.\n"
        + inputFile.getName() + " has " + formatComma.format(inputSize)
        + " bytes."));
      refreshDataSize();          // bring back previous nibble data and size
      return;                     // we can't open this file, so give up
    }
    else if (inputSize > 99999999L) // just short of 100 megabytes
    {
      if (JOptionPane.showConfirmDialog(mainFrame,
        ("Files larger than 100 megabytes may be slow.\n"
        + inputFile.getName() + " has " + formatComma.format(inputSize)
        + " bytes.\nDo you want to open this file anyway?"),
        "Large File Warning", JOptionPane.YES_NO_CANCEL_OPTION)
        != JOptionPane.YES_OPTION)
      {
        refreshDataSize();        // bring back previous nibble data and size
        return;                   // user doesn't want to open the new file
      }
    }

    /* Read 8-bit data bytes from the input file and convert to pairs of 4-bit
    nibbles.  Since we are reading whole bytes, there is always an even number
    of nibbles. */

    try                           // catch file I/O errors, memory allocation
    {
      buffer = new byte[BUFFER_SIZE]; // allocate byte buffer for input
      inputStream = new FileInputStream(inputFile);
                                  // try to open input file
      nibbleData = new HexEdit2Data(2 * (int) inputSize);
                                  // allocate nibble data object from file size
      while ((length = inputStream.read(buffer, 0, BUFFER_SIZE)) > 0)
      {
        for (i = 0; i < length; i ++)
        {
          nibbleData.append((buffer[i] >> NIBBLE_SHIFT) & NIBBLE_MASK);
                                  // high-order nibble in byte
          nibbleData.append(buffer[i] & NIBBLE_MASK);
                                  // low-order nibble in byte
        }
      }
      inputStream.close();        // try to close input file
      mainFrame.setTitle("Hex File Editor - " + inputFile.getName());
    }
    catch (IOException ioe)       // most likely I/O error is "file not found"
    {
      mainFrame.setTitle(PROGRAM_TITLE); // remove file name from title bar
      nibbleData = new HexEdit2Data(0); // substitute an empty data object
      JOptionPane.showMessageDialog(mainFrame,
        ("Can't read from input file:\n" + ioe.getMessage()));
    }
    catch (OutOfMemoryError oome) // not enough memory to open the file
    {
      mainFrame.setTitle(PROGRAM_TITLE); // remove file name from title bar
      nibbleData = new HexEdit2Data(0); // substitute an empty data object
      JOptionPane.showMessageDialog(mainFrame,
        ("Not enough memory to open this file.\n"
        + inputFile.getName() + " has "+ formatComma.format(inputSize)
        + " bytes.\nTry increasing the Java heap size with the -Xmx option."));
    }
    refreshDataSize();            // set to correct number of data nibbles

  } // end of openFile() method


/*
  pasteHex() method

  Paste the clipboard as hex digits at the cursor location.  Complain to the
  user if there is anything except hex digits, space, or common punctuation.
*/
  static void pasteHex()
  {
    char ch;                      // one character from clipboard string
    int hexValue;                 // integer value of one hexadecimal digit
    int i;                        // index variable
    int length;                   // length of clipboard string in characters
    int[] nibbles;                // nibble values created from <text>
    String text;                  // string obtained from clipboard
    int used;                     // number of nibbles used in <nibbles>

    text = getClipboard();        // get user's string from clipboard
    length = text.length();       // get number of characters in user's string
    nibbles = new int[length];    // allocate maximum array that may be needed
    used = 0;                     // start placing nibbles at this array index
    for (i = 0; i < length; i ++) // do all characters in the string
    {
      ch = text.charAt(i);        // get one character from clipboard string
      hexValue = charHexValue(ch); // convert character to value of hex digit
      if (hexValue >= 0)          // was it a valid hexadecimal digit?
        nibbles[used ++] = hexValue; // yes, save the nibble value
      else if (hexValue == HEX_IGNORE) // ignore spaces and punctuation?
        { /* do nothing */ }
      else                        // illegal character
      {
        JOptionPane.showMessageDialog(mainFrame,
          ("Clipboard string must be hexadecimal digits or spaces; found "
          + (Character.isISOControl(ch) ? "" : ("\"" + ch + "\" or ")) + "0x"
          + Integer.toHexString(ch).toUpperCase() + "."));
        return;                   // return early: cancel the paste operation
      }
    }
    pasteNibbles(nibbles, used, overFlag); // paste nibbles as file data

  } // end of pasteHex() method


/*
  pasteNibbles() method

  Called by both pasteHex() and pasteText() to paste an array of nibbles at the
  cursor location.  Handles insert/overwrite modes and selections.
*/
  static void pasteNibbles(
    int[] array,                  // 4-bit nibble values (may not be full)
    int used,                     // actual number of nibbles used in array
    boolean localOver)            // global <overFlag> or local true/false
  {
    int beginIndex, endIndex;     // nibble index variables
    int i;                        // index variable

    if (used <= 0)                // is there any real work to do?
      return;                     // no: clipboard not available, not a string,
                                  // ... or an empty string
    else if (localOver)           // are we in overwrite mode?
    {
      /* Clipboard replaces or "overwrites" starting at the cursor, or appends
      if there is more clipboard than data after the cursor. */

      beginIndex = Math.min(textPanel.cursorDot, textPanel.cursorMark);
      endIndex = Math.max(textPanel.cursorDot, textPanel.cursorMark);

      if ((beginIndex < endIndex) && (used != (endIndex - beginIndex)))
      {
        JOptionPane.showMessageDialog(mainFrame, ("Overwrite selection ("
          + (endIndex - beginIndex) + ") and clipboard (" + used
          + ") have different sizes."));
        return;                   // return early: cancel the paste operation
      }

      textPanel.cursorDot = beginIndex; // start replacing here
      for (i = 0; i < used; i ++) // replace with nibbles given by caller
        nibbleData.put((textPanel.cursorDot ++), array[i]);
    }

    else                          // must be insert mode
    {
      /* Clipboard inserts starting at the cursor.  Any selection is replaced
      by first deleting the selection, then inserting the clipboard. */

      deleteSelected();           // delete current selection, if any
      for (i = 0; i < used; i ++) // insert all nibbles given by caller
        nibbleData.insert((textPanel.cursorDot ++), array[i]);
    }

    textPanel.cursorMark = textPanel.cursorDot;
                                  // position cursor after inserted bytes
    textPanel.limitCursorRange(); // refresh data size, enforce cursor range
    textPanel.makeVisible(textPanel.cursorDot);
                                  // make sure that user can see cursor
    textPanel.adjustScrollBar();  // adjust scroll bar to match new position
    textPanel.repaint();          // redraw text display as necessary

  } // end of pasteNibbles() method


/*
  pasteText() method

  Paste the clipboard as ASCII text at the cursor location.  We accept all
  characters, because they are converted to a byte array using the local
  system's default encoding.  We treat the bytes as a string of nibbles; no
  attempt is made to align them on a byte boundary.  This may produce results
  that are unexpected, but which are logically consistent.
*/
  static void pasteText()
  {
    byte[] bytes;                 // array of bytes obtained from a string
    int i;                        // index variable
    int[] nibbles;                // nibble values created from <bytes>
    int used;                     // number of nibbles used in <nibbles>

    bytes = getClipboard().getBytes(); // convert clipboard to bytes
    nibbles = new int[bytes.length * 2]; // always uses full array
    used = 0;                     // start placing nibbles at this array index
    for (i = 0; i < bytes.length; i ++) // do all bytes in the string
    {
      nibbles[used ++] = (bytes[i] >> NIBBLE_SHIFT) & NIBBLE_MASK;
                                  // high-order nibble in byte
      nibbles[used ++] = bytes[i] & NIBBLE_MASK;
                                  // low-order nibble in byte
    }
    pasteNibbles(nibbles, used, overFlag); // paste nibbles as file data

  } // end of pasteText() method


/*
  refreshDataSize() method

  Recalculate the total number of data nibbles, after insertions and deletions.
*/
  static void refreshDataSize()
  {
    nibbleCount = nibbleData.size(); // refetch total number of data nibbles
  }


/*
  saveFile() method

  Ask the user for an output file name, create or replace that file, and copy
  the contents of our nibble data to that file.
*/
  static void saveFile()
  {
    byte[] buffer;                // output buffer (faster than byte-by-byte)
    int i;                        // index variable
    int length;                   // number of bytes in output buffer
    File outputFile;              // user's selected output file
    FileOutputStream outputStream; // output file stream

    /* Ask the user for an output file name. */

    fileChooser.setDialogTitle("Save File...");
    if (fileChooser.showSaveDialog(mainFrame) != JFileChooser.APPROVE_OPTION)
      return;                     // user cancelled file selection dialog box
    outputFile = fileChooser.getSelectedFile(); // get user's output file

    /* Convert pairs of 4-bit data nibbles to 8-bit bytes and write to the
    output file.  There may be an odd number of nibbles; in which case, assume
    a zero for the final nibble. */

    try                           // catch file I/O errors
    {
      if (canWriteFile(outputFile)) // if writing this file seems safe
      {
        buffer = new byte[BUFFER_SIZE]; // allocate byte buffer for output
        length = 0;               // nothing in output buffer yet
        outputStream = new FileOutputStream(outputFile);
                                  // try to open output file
        refreshDataSize();        // refresh total number of nibbles

        i = 0;                    // start with first data nibble
        while (i < nibbleCount)   // do all nibbles
        {
          if (length >= BUFFER_SIZE) // time to empty the output buffer?
          {
            outputStream.write(buffer); // yes, write entire buffer
            length = 0;           // and now there is nothing in the buffer
          }
          buffer[length] = (byte) (nibbleData.get(i ++) << NIBBLE_SHIFT);
                                  // can always get high-order nibble
          if (i < nibbleCount)    // there may not be a low-order nibble
          {
            buffer[length] |= (byte) nibbleData.get(i ++);
                                  // insert bits for low-order nibble
          }
          length ++;              // there is one more byte in output buffer
        }
        if (length > 0)           // is there more output to be written?
          outputStream.write(buffer, 0, length); // yes, write partial buffer
        outputStream.close();     // try to close output file
      }
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(mainFrame,
        ("Can't write to output file:\n" + ioe.getMessage()));
    }
  } // end of saveFile() method


/*
  searchConvertNibbles() method

  Given a text string, convert it to an integer array of nibbles, using the
  search dialog's options for hex or text conversion.  The given string should
  not be empty, because we use an empty array as a result to indicate an error.
*/
  static int[] searchConvertNibbles(
    String text,                  // user's string to be converted
    String type)                  // description like "replacement" or "search"
  {
    byte[] bytes;                 // array of bytes obtained from text string
    char ch;                      // one character from hex string
    boolean error;                // true if error occurs duing hex conversion
    int hexValue;                 // integer value of one hexadecimal digit
    int i;                        // index variable
    int length;                   // length of hex string in characters
    int[] nibbles;                // nibble values created from hex string
    int[] result;                 // nibble array that we return to the caller
    int used;                     // number of nibbles used in <nibbles>

    if (searchIsHex.isSelected()) // is this a hex search?
    {
      error = false;              // assume no errors during conversion
      length = text.length();     // get number of characters in user's string
      nibbles = new int[length];  // allocate maximum array that may be needed
      used = 0;                   // start placing nibbles at this array index
      for (i = 0; i < length; i ++) // do all characters in the string
      {
        ch = text.charAt(i);      // get one character from clipboard string
        hexValue = charHexValue(ch); // convert character to value of hex digit
        if (hexValue >= 0)        // was it a valid hexadecimal digit?
          nibbles[used ++] = hexValue; // yes, save the nibble value
        else if (hexValue == HEX_IGNORE) // ignore spaces and punctuation?
          { /* do nothing */ }
        else                      // illegal character
        {
          showSearchMessage("Invalid hex digit in " + type + " string; found "
            + (Character.isISOControl(ch) ? "" : ("\"" + ch + "\" or ")) + "0x"
            + Integer.toHexString(ch).toUpperCase() + ".");
          error = true;           // flag this as an error condition
          break;                  // escape early from <for> loop
        }
      }
      if (error)                  // was an invalid hex digit found?
        used = 0;                 // yes, force result to be empty
      else if (used == 0)         // did we find any valid hex digits?
      {
        showSearchMessage("No valid hex digits found in " + type + " string.");
      }
      result = new int[used];     // recopy array with only the portion used
      for (i = 0; i < used; i ++)
        result[i] = nibbles[i];
    }
    else                          // must be a text search, which never fails
    {
      bytes = text.getBytes();    // convert string to bytes, default encoding
      result = new int[bytes.length * 2]; // always uses full array
      used = 0;                   // start placing nibbles at this array index
      for (i = 0; i < bytes.length; i ++) // do all bytes in the string
      {
        result[used ++] = (bytes[i] >> NIBBLE_SHIFT) & NIBBLE_MASK;
                                  // high-order nibble in byte
        result[used ++] = bytes[i] & NIBBLE_MASK;
                                  // low-order nibble in byte
      }
    }
    return(result);               // give caller whatever we could find

  } // end of searchConvertNibbles() method


/*
  searchFindFirst() method

  Find the first occurrence of the current search string (if any).
*/
  static void searchFindFirst()
  {
    searchFindNext(0);            // like "Find Next" but start at beginning
  }


/*
  searchFindNext() method

  Find the next occurrence of the current search string (if any).  The user can
  and may change options, text fields, etc, between searches.  So this method
  should really be called "find a search string starting from the current
  cursor location or after the current selection if any".  Much easier to say
  "Find Next"!
*/
  static void searchFindNext()
  {
    searchFindNext(Math.max(textPanel.cursorDot, textPanel.cursorMark));
  }

  static void searchFindNext(
    int givenStart)               // data nibble index where search begins
  {
    boolean byteFlag;             // true if start searching on byte boundaries
    int i;                        // index variable
    boolean matchFlag;            // true if we found a match
    int[] nibbles;                // nibble array obtained from <text>
    boolean nullFlag;             // true if null bytes ignored in data
    int start;                    // index that starts current comparison
    String text;                  // search string as typed by user

    if (searchDialog == null)     // has the search dialog been created?
    {
      showSearchDialog();         // be nice and start the find/replace dialog
      return;
    }
    text = searchFindText.getText(); // get user's string
    if (text.length() == 0)       // did the user type anything?
    {
      showSearchDialog();         // bring up the full find/replace dialog
      showSearchMessage("Empty strings are found everywhere.  (Joke.)");
      searchFindText.requestFocusInWindow(); // we need the search string
      return;
    }
    nibbles = searchConvertNibbles(text, "search");
    if (nibbles.length == 0)      // was there an error during conversion?
      return;                     // yes, error message already printed

    /* If we got this far, there is nothing to report and we should cancel any
    previous status message in the search dialog box. */

    searchStatus.setText(EMPTY_STATUS); // clear any previous search status

    /* We start looking at the location given by the caller, which is usually
    after the current selection. */

    byteFlag = searchByteBound.isSelected(); // true if start on byte boundary
    matchFlag = false;            // no match found yet
    nullFlag = searchIgnoreNulls.isSelected() && ((nibbles.length % 2) == 0);
                                  // true if can ignore nulls, if full bytes
    start = givenStart;           // index that starts current comparison
    if (byteFlag)                 // does user want searches to be full bytes?
      start += start % 2;         // yes, round up starting nibble index

    /* For each acceptable starting index (byte or nibble boundary), try to
    match the user's string as-is.  If that fails, and the null flag is true,
    try again ignoring null bytes in the data.  Exactly one null byte appears
    when plain text (7-bit ASCII) characters are encoded in Unicode.  Some East
    Asian encodings like inserting null bytes for alignment reasons. */

    while ((nibbleCount - start) >= nibbles.length) // keep looking
    {
      /* First try the search string as-is.  This is a straight nibble-by-
      nibble comparison, no matter what the options may be. */

      boolean differFlag = false; // assume that comparison is successful
      for (i = 0; i < nibbles.length; i ++)
      {
        if (nibbles[i] != nibbleData.get(start + i))
        {
          differFlag = true;      // comparison has failed
          break;                  // escape early from inner <for> loop
        }
      }
      if (differFlag == false)    // was the comparison successful?
      {
        matchFlag = true;         // indicate that we were successful
        textPanel.cursorMark = start; // yes, set start of selection
        textPanel.cursorDot = start + nibbles.length; // set end of selection
        break;                    // escape early from outer <while> loop
      }

      /* Try again starting from the same location.  This time, if a whole byte
      differs, and the nibble data for that byte is null (0x00), advance to the
      next byte of nibble data.  Null search bytes will be matched to the first
      null data byte.  Once a byte is matched, the following does not backtrack
      if a later byte comparison fails. */

      if (nullFlag && ((start % 2) == 0)) // if nulls ignored and start on byte
      {
        int dataIndex = start;    // where we are looking in the nibble data
        differFlag = false;       // assume that comparison is successful
        int findIndex = 0;        // where we are comparing from search data
        while ((dataIndex < (nibbleCount - 1)) // while there are data bytes
          && (findIndex < (nibbles.length - 1))) // and there are search bytes
        {
          int dataByte = (nibbleData.get(dataIndex) << NIBBLE_SHIFT)
            | nibbleData.get(dataIndex + 1);
                                  // construct one byte of nibble data
          int findByte = (nibbles[findIndex] << NIBBLE_SHIFT)
            | nibbles[findIndex + 1]; // construct one byte of search data
          if (dataByte == findByte) // does file data match search string?
          {
            dataIndex += 2;       // yes, index of next file data byte
            findIndex += 2;       // index of next search string byte
          }
          else if ((dataByte == 0x00) && (findIndex > 0)) // in between null?
          {
            dataIndex += 2;       // ignore null data bytes, but after matching
                                  // ... at least one byte of search string
          }
          else
          {
            differFlag = true;    // comparison has failed
            break;                // escape early from inner <while> loop
          }
        }
        if ((differFlag == false) && (findIndex == nibbles.length))
                                  // was the comparison successful?
        {
          matchFlag = true;       // indicate that we were successful
          textPanel.cursorMark = start; // yes, set start of selection
          textPanel.cursorDot = dataIndex; // set end of selection
          break;                  // escape early from outer <while> loop
        }
      }

      /* We were unable to match the search string at this starting index.
      Advance to the next possible starting index. */

      start += byteFlag ? 2 : 1;  // advance by bytes or by nibbles
    }

    /* Show the results of our search. */

    if (matchFlag)                // were we successful?
    {
      textPanel.makeVisible(textPanel.cursorMark);
                                  // do try to show start of selection
      textPanel.makeVisible(textPanel.cursorDot);
                                  // but end of selection is more important
      textPanel.adjustScrollBar(); // adjust scroll bar to match new position
      textPanel.repaint();        // redraw text display as necessary
    }
    else                          // no, search failed
      showSearchMessage("Search string not found.");

  } // end of searchFindNext() method


/*
  searchReplaceThis() method

  Replace the current selection (if any) with the replacement string (if any).
  Since there is no "Undo" feature yet, we replace only if there is an active
  selection and the replacement string is not empty (no "search-and-delete"
  allowed).  There is no "Replace All" feature in this program (too dangerous).
*/
  static void searchReplaceThis()
  {
    int[] nibbles;                // nibble array obtained from <text>
    String text;                  // replacement string as typed by user

    if (searchDialog == null)     // has the search dialog been created?
    {
      showSearchDialog();         // be nice and start the find/replace dialog
      if (textPanel.cursorDot != textPanel.cursorMark) // if selection exists
        searchReplaceText.requestFocusInWindow(); // only need replace string
      return;
    }
    if (textPanel.cursorDot == textPanel.cursorMark) // is there a selection?
    {
      showSearchMessage("There is no selection to replace.");
      return;                     // nothing to do, so return to caller
    }
    text = searchReplaceText.getText(); // get user's string
    if (text.length() == 0)       // did the user type anything?
    {
      showSearchDialog();         // bring up the full find/replace dialog
      showSearchMessage("Replacement with an empty string is not supported.");
      searchReplaceText.requestFocusInWindow(); // we need the replace string
      return;
    }
    nibbles = searchConvertNibbles(text, "replace");
    if (nibbles.length == 0)      // was there an error during conversion?
      return;                     // yes, error message already printed

    /* If we got this far, there is nothing to report and we should cancel any
    previous status message in the search dialog box. */

    searchStatus.setText(EMPTY_STATUS); // clear any previous search status

    /* We always insert, never overwrite, so pull the rug out from under the
    pasteNibbles() method, which takes care of deleting the selection. */

    pasteNibbles(nibbles, nibbles.length, false); // paste nibbles as file data

  } // end of searchReplaceThis() method


/*
  selectAll() method

  Select all data nibbles from the beginning to the end.  Leave the cursor in
  whatever region is active.  (Don't change the <cursorOnText> variable.)
*/
  static void selectAll()
  {
    textPanel.cursorDot = nibbleCount; // current cursor ends selection
    textPanel.cursorMark = 0;     // select data from beginning of file
    textPanel.repaint();          // redraw text display as necessary
  }


/*
  setClipboard() method

  Place a string onto the clipboard for some other application to read.
*/
  static void setClipboard(String text)
  {
    clipString = text;            // save string reference for later
    try                           // clipboard may not be available
    {
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
        (Transferable) userActions, null); // place data notice on clipboard
    }
    catch (IllegalStateException ise)
    {
      JOptionPane.showMessageDialog(mainFrame,
        ("Can't put text on clipboard:\n" + ise.getMessage()));
    }
  } // end of setClipboard() method


/*
  showEditMenu() method

  Display the pop-up "edit menu" at a location relative to a given component
  and (x,y) co-ordinates.  Items in the menu are enabled or disabled according
  to approximate context.
*/
  static void showEditMenu(Component invoker, int x, int y, boolean mouse)
  {
    boolean clipboard = getClipboard().length() > 0; // if text on clipboard
    boolean content = nibbleCount > 0; // true if there is any file data
    boolean selection = textPanel.cursorDot != textPanel.cursorMark;
                                  // true if there is a current selection

    menuPopup = new JPopupMenu();

    menuCopyDump.setEnabled(selection);
    menuPopup.add(menuCopyDump);
    menuCopyHex.setEnabled(selection);
    menuPopup.add(menuCopyHex);
    menuCopyText.setEnabled(selection);
    menuPopup.add(menuCopyText);
    menuPasteHex.setEnabled(clipboard);
    menuPopup.add(menuPasteHex);
    menuPasteText.setEnabled(clipboard);
    menuPopup.add(menuPasteText);

    menuPopup.addSeparator();

    menuFind.setEnabled(content);
    menuPopup.add(menuFind);
    menuNext.setEnabled(content);
    menuPopup.add(menuNext);
    menuReplace.setEnabled(selection);
    menuPopup.add(menuReplace);

    menuPopup.addSeparator();

    menuDelete.setEnabled(selection);
    menuPopup.add(menuDelete);
    menuSelect.setEnabled(content);
    menuPopup.add(menuSelect);

    if (mouse == false)           // Edit button only, not right mouse click
    {
      menuPopup.addSeparator();
      menuCopyCursor.setEnabled(true);
      menuPopup.add(menuCopyCursor);
      menuGotoOffset.setEnabled(content);
      menuPopup.add(menuGotoOffset);
    }

    menuPopup.show(invoker, x, y); // show menu with context-sensitive items

  } // end of showEditMenu() method


/*
  showGotoDialog() method

  Show the "Go To File Offset" dialog box.  We may have to create it first.
  This is a much simpler layout and easier interaction than <searchDialog>.
*/
  static void showGotoDialog()
  {
    if (gotoDialog == null)       // has the dialog box been created yet?
    {
      /* Create a vertical box to stack buttons and options. */

      JPanel panel1 = new JPanel();
      panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

      /* First layout line has the "go to" address input string. */

      JPanel panel2 = new JPanel(new BorderLayout(10, 0));

      JLabel label1 = new JLabel("Go to file offset:", JLabel.RIGHT);
      if (buttonFont != null) label1.setFont(buttonFont);
      panel2.add(label1, BorderLayout.WEST);

      gotoOffsetText = new JTextField("", 15);
      gotoOffsetText.addActionListener(userActions);
      if (buttonFont != null) gotoOffsetText.setFont(buttonFont);
      gotoOffsetText.setMargin(TEXT_MARGINS);
      panel2.add(gotoOffsetText, BorderLayout.CENTER);
      panel1.add(panel2);
      panel1.add(Box.createVerticalStrut(10)); // vertical space

      /* Second layout line has a message string for the "go to" status. */

      JPanel panel3 = new JPanel(new BorderLayout(0, 0));
      gotoStatus = new JLabel(EMPTY_STATUS, JLabel.CENTER);
      if (buttonFont != null) gotoStatus.setFont(buttonFont);
      showGotoRange(-1);          // set message size with maximum hex digits
      panel3.add(gotoStatus, BorderLayout.CENTER);
      panel1.add(panel3);
      panel1.add(Box.createVerticalStrut(20));

      /* Third and last line has the action buttons. */

      JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));

      gotoJumpButton = new JButton("Go");
      gotoJumpButton.addActionListener(userActions);
      if (buttonFont != null) gotoJumpButton.setFont(buttonFont);
      gotoJumpButton.setMnemonic(KeyEvent.VK_G);
      gotoJumpButton.setToolTipText("Jump to file offset given above.");
      panel4.add(gotoJumpButton);

      gotoCloseButton = new JButton("Close");
      gotoCloseButton.addActionListener(userActions);
      if (buttonFont != null) gotoCloseButton.setFont(buttonFont);
      gotoCloseButton.setMnemonic(KeyEvent.VK_C);
      gotoCloseButton.setToolTipText("Close this dialog box.");
      panel4.add(gotoCloseButton);

      panel1.add(panel4);

      /* Put the vertical box inside a flow layout to center it horizontally
      and stop expansion.  Add left and right margins with the flow layout. */

      JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
      panel5.add(panel1);         // align vertical box inside horizontal box

      /* Put the flow layout in another vertical box to center vertically. */

      Box panel6 = Box.createVerticalBox(); // create a basic vertical box
      panel6.add(Box.createGlue()); // stretch to the top
      panel6.add(Box.createVerticalStrut(20)); // top margin
      panel6.add(panel5);         // horizontal flow layout
      panel6.add(Box.createVerticalStrut(20)); // bottom margin
//    panel6.add(Box.createGlue()); // stretch to bottom (assumed by layout)

      /* Position the dialog box.  Like a JFrame, a JDialog doesn't have an
      initial size.  We "pack" our JDialog layout to the minimum size. */

      gotoDialog = new JDialog((Frame) null, "Go To File Offset");
      gotoDialog.getContentPane().add(panel6, BorderLayout.CENTER);
      gotoDialog.pack();          // lay out components, set preferred size
      gotoDialog.setLocation(mainFrame.getX() + 50, mainFrame.getY() + 50);

      /* The status message controls the layout width, so fix the size, to
      avoid the input field changing size when the message text changes. */

      gotoStatus.setPreferredSize(gotoStatus.getPreferredSize());
    }

    refreshDataSize();            // set to correct number of data nibbles
    showGotoRange(nibbleCount / 2); // default to message about range allowed
    if (gotoDialog.isVisible() == false) // if dialog is closed or hidden
      gotoOffsetText.requestFocusInWindow();
                                  // assume user wants to edit offset string
    gotoDialog.setVisible(true);  // show "go to" dialog or bring to the front

  } // end of showGotoDialog() method


/*
  showGotoRange() method

  Set the status in the "Go To File Offset" dialog box to an informational
  message about the hexadecimal range allowed.
*/
  static void showGotoRange(int maxValue)
  {
    gotoStatus.setText("Enter a byte offset in hexadecimal from 0 to "
      + Integer.toHexString(maxValue).toUpperCase() + " and click \"Go\".");
  }


/*
  showHelp() method

  Show the help summary.  This is a UNIX standard and is expected for all
  console applications, even very simple ones.
*/
  static void showHelp()
  {
    System.err.println();
    System.err.println(PROGRAM_TITLE);
    System.err.println();
    System.err.println("This is a graphical application.  You may give options and/or one file name on");
    System.err.println("the command line.  Options are:");
    System.err.println();
    System.err.println("  -? = -help = show summary of command-line syntax");
    System.err.println("  -d4 -d8 -d12 -d16 -d24 -d32 = input bytes per dump line (default: "
      + DEFAULT_DUMP + ")");
    System.err.println("  -ins = keyboard input starts with insert mode (default)");
    System.err.println("  -over = keyboard input starts with overwrite mode");
    System.err.println("  -u# = font size for buttons, dialogs, etc; default is local system;");
    System.err.println("      example: -u16");
    System.err.println("  -w(#,#,#,#) = normal window position: left, top, width, height;");
    System.err.println("      example: -w(50,50,700,500)");
    System.err.println("  -x = maximize application window; default is normal window");
    System.err.println();
    System.err.println(COPYRIGHT_NOTICE);
//  System.err.println();

  } // end of showHelp() method


/*
  showSearchDialog() method

  Show the "Find or Replace" dialog box.  We may have to create it first.  This
  layout is more complicated than the "Go To File Offset" dialog.  Also, user
  keystrokes (Control-F, Control-N, etc) may invoke methods related to dialog
  components before the dialog box has actually been created.
*/
  static void showSearchDialog()
  {
    GridBagConstraints gbc;       // reuse the same constraint object

    if (searchDialog == null)     // has the dialog box been created yet?
    {
      /* Create the search dialog using a grid bag layout.  Most of this code
      is just plain ugly.  There isn't much chance of understanding it unless
      you read the documentation for GridBagLayout ... if you can understand
      that!  As with our main frame, some intermediate panel names are of no
      lasting importance and hence are only numbered (panel1, panel2, etc). */

      JPanel panel1 = new JPanel(new GridBagLayout()); // use a grid bag layout
      gbc = new GridBagConstraints(); // modify and reuse these constraints

      /* First layout line has the "find" or "search" string. */

      JLabel label1 = new JLabel("Search for:");
      if (buttonFont != null) label1.setFont(buttonFont);
      gbc.anchor = GridBagConstraints.EAST;
      gbc.fill = GridBagConstraints.NONE;
      gbc.gridwidth = 1;
      panel1.add(label1, gbc);
      panel1.add(Box.createHorizontalStrut(10), gbc); // horizontal space

      searchFindText = new JTextField("", 20);
      searchFindText.addActionListener(userActions);
      if (buttonFont != null) searchFindText.setFont(buttonFont);
      searchFindText.setMargin(TEXT_MARGINS);
      gbc.anchor = GridBagConstraints.WEST;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      panel1.add(searchFindText, gbc);
      panel1.add(Box.createVerticalStrut(10), gbc); // vertical space

      /* Second layout line has the "replace" string. */

      JLabel label2 = new JLabel("Replace with:");
      if (buttonFont != null) label2.setFont(buttonFont);
      gbc.anchor = GridBagConstraints.EAST;
      gbc.fill = GridBagConstraints.NONE;
      gbc.gridwidth = 1;
      panel1.add(label2, gbc);
      panel1.add(Box.createHorizontalStrut(10), gbc);

      searchReplaceText = new JTextField("", 20);
      searchReplaceText.addActionListener(userActions);
      if (buttonFont != null) searchReplaceText.setFont(buttonFont);
      searchReplaceText.setMargin(TEXT_MARGINS);
      gbc.anchor = GridBagConstraints.WEST;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      panel1.add(searchReplaceText, gbc);
      panel1.add(Box.createVerticalStrut(10), gbc);

      /* Third layout line has the options: radio buttons and check boxes.  Not
      all of these require action listeners. */

      ButtonGroup group1 = new ButtonGroup();
      JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

      searchIsHex = new JRadioButton("hex string", true);
      if (buttonFont != null) searchIsHex.setFont(buttonFont);
      searchIsHex.setMnemonic(KeyEvent.VK_H);
      searchIsHex.setToolTipText(
        "Search and replace strings are hexadecimal digits.");
      group1.add(searchIsHex);
      panel3.add(searchIsHex);

      searchIsText = new JRadioButton("text string", false);
      if (buttonFont != null) searchIsText.setFont(buttonFont);
      searchIsText.setMnemonic(KeyEvent.VK_T);
      searchIsText.setToolTipText(
        "Search and replace strings are regular text.");
      searchIsText.addActionListener(userActions);
                                  // do last so this doesn't fire early
      group1.add(searchIsText);
      panel3.add(searchIsText);

      searchByteBound = new JCheckBox("byte boundary", false);
      if (buttonFont != null) searchByteBound.setFont(buttonFont);
      searchByteBound.setMnemonic(KeyEvent.VK_B);
      searchByteBound.setToolTipText(
        "Search starts on byte boundary.  Does not apply to replace.");
      panel3.add(searchByteBound);

      searchIgnoreNulls = new JCheckBox("ignore nulls", false);
      if (buttonFont != null) searchIgnoreNulls.setFont(buttonFont);
      searchIgnoreNulls.setMnemonic(KeyEvent.VK_I);
      searchIgnoreNulls.setToolTipText(
        "Ignore null data bytes between search bytes.");
      panel3.add(searchIgnoreNulls);

      gbc.anchor = GridBagConstraints.CENTER;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      panel1.add(panel3, gbc);
      panel1.add(Box.createVerticalStrut(10), gbc);

      /* Fourth layout line has a message string for the search status. */

      searchStatus = new JLabel(EMPTY_STATUS, JLabel.CENTER);
      if (buttonFont != null) searchStatus.setFont(buttonFont);
      gbc.anchor = GridBagConstraints.CENTER;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      panel1.add(searchStatus, gbc);
      panel1.add(Box.createVerticalStrut(20), gbc);

      /* Fifth and last line has the action buttons. */

      JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));

      searchFindButton = new JButton("Find First");
      searchFindButton.addActionListener(userActions);
      if (buttonFont != null) searchFindButton.setFont(buttonFont);
      searchFindButton.setMnemonic(KeyEvent.VK_F);
      searchFindButton.setToolTipText(
        "Find first occurrence of search string in file.");
      panel4.add(searchFindButton);

      searchNextButton = new JButton("Find Next");
      searchNextButton.addActionListener(userActions);
      if (buttonFont != null) searchNextButton.setFont(buttonFont);
      searchNextButton.setMnemonic(KeyEvent.VK_N);
      searchNextButton.setToolTipText(
        "Find next occurrence of search string.");
      panel4.add(searchNextButton);

      searchReplaceButton = new JButton("Replace");
      searchReplaceButton.addActionListener(userActions);
      if (buttonFont != null) searchReplaceButton.setFont(buttonFont);
      searchReplaceButton.setMnemonic(KeyEvent.VK_R);
      searchReplaceButton.setToolTipText(
        "Replace current selection or previously found string.");
      panel4.add(searchReplaceButton);

      searchCloseButton = new JButton("Close");
      searchCloseButton.addActionListener(userActions);
      if (buttonFont != null) searchCloseButton.setFont(buttonFont);
      searchCloseButton.setMnemonic(KeyEvent.VK_C);
      searchCloseButton.setToolTipText("Close this dialog box.");
      panel4.add(searchCloseButton);

      gbc.anchor = GridBagConstraints.CENTER;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      panel1.add(panel4, gbc);

      /* The layout in a grid bag goes strange if there isn't enough space.
      Box the grid bag inside a flow layout to center it horizontally and stop
      expansion, then inside a plain box to center it vertically. */

      JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
      panel5.add(panel1);         // put grid bag in a fancy horizontal box

      Box panel6 = Box.createVerticalBox(); // create a basic vertical box
      panel6.add(Box.createGlue()); // stretch to the top
      panel6.add(Box.createVerticalStrut(20)); // top margin
      panel6.add(panel5);         // put boxed grid bag in center
      panel6.add(Box.createVerticalStrut(20)); // bottom margin
//    panel6.add(Box.createGlue()); // stretch to bottom (assumed by layout)

      /* Position the dialog box.  Like a JFrame, a JDialog doesn't have an
      initial size.  We "pack" our JDialog layout to the minimum size. */

      searchDialog = new JDialog((Frame) null, "Find or Replace");
      searchDialog.getContentPane().add(panel6, BorderLayout.CENTER);
      searchDialog.pack();        // lay out components, set preferred size
      searchDialog.setLocation(mainFrame.getX() + 50, mainFrame.getY() + 50);
    }

    searchStatus.setText(EMPTY_STATUS); // clear any previous search status
    if (searchDialog.isVisible() == false) // if dialog is closed or hidden
      searchFindText.requestFocusInWindow();
                                  // assume user wants to edit search string
    searchDialog.setVisible(true); // show search dialog or bring to the front

  } // end of showSearchDialog() method


/*
  showSearchMessage() method

  Display a text string given by the caller.  If the search dialog is open,
  then use the message field in the dialog.  Otherwise, use a pop-up dialog.
*/
  static void showSearchMessage(String text)
  {
    if ((searchDialog != null) && searchDialog.isVisible())
    {
      searchStatus.setText(text); // place search result in message field
      searchDialog.setVisible(true); // bring search dialog to the front
    }
    else
      JOptionPane.showMessageDialog(mainFrame, text); // simpler pop-up dialog
  }


/*
  userButton() method

  This method is called by our action listener actionPerformed() to process
  buttons, in the context of the main HexEdit2 class.
*/
  static void userButton(ActionEvent event)
  {
    try                           // "out of memory" errors are likely
    {
      Object source = event.getSource(); // where the event came from
      if (source == dumpWidthDialog) // number of input bytes per dump line
      {
        /* We can safely parse the dump width as an integer, because we supply
        the only choices allowed, and the user can't edit this dialog field.
        Calling adjustScrollBar() or makeVisible() is not possible here,
        because display sizes are unknown until after the first redraw. */

        dumpWidth = Integer.parseInt((String) dumpWidthDialog
          .getSelectedItem());    // convert text width to integer
        textPanel.repaint();      // redraw text display as necessary
      }
      else if (source == exitButton) // "Exit" button
      {
        System.exit(0);           // always exit with zero status from GUI
      }
      else if (source == fontNameDialog) // font name for display text area
      {
        /* We can safely assume that the font name is valid, because we
        obtained the names from getAvailableFontFamilyNames(), and the user
        can't edit this dialog field.  Calling adjustScrollBar() or
        makeVisible() is not possible here, because display sizes are unknown
        until after the first redraw. */

        fontName = (String) fontNameDialog.getSelectedItem();
        textPanel.repaint();      // redraw text display as necessary
      }
      else if (source == gotoCloseButton) // "Close" button on "go to" dialog
      {
        gotoDialog.setVisible(false); // hide "Go To File Offset" dialog box
      }
      else if (source == gotoJumpButton) // "Go" button on "Go To File Offset"
      {
        gotoFileOffset();         // call common method for this operation
      }
      else if (source == gotoOffsetText) // press Enter on "go to" offset text
      {
        gotoFileOffset();         // call common method for this operation
      }
      else if (source == menuButton) // "Edit Menu" button
      {
        showEditMenu(menuButton, 0, menuButton.getHeight(), false);
      }
      else if (source == menuCopyCursor) // "Copy Cursor Offset" menu item
      {
        copyCursor();             // call common method for this operation
      }
      else if (source == menuCopyDump) // "Copy Dump" menu item (selection)
      {
        copyDump();               // call common method for this operation
      }
      else if (source == menuCopyHex) // "Copy Hex" menu item (selection)
      {
        copyHex();                // call common method for this operation
      }
      else if (source == menuCopyText) // "Copy Text" menu item (selection)
      {
        copyText();               // call common method for this operation
      }
      else if (source == menuDelete) // "Delete" menu item (selection)
      {
        deleteSelected();         // call common method for this operation
      }
      else if (source == menuFind) // "Find" menu item
      {
        showSearchDialog();       // call common method for this operation
      }
      else if (source == menuGotoOffset) // "Go To File Offset" menu item
      {
        showGotoDialog();         // call common method for this operation
      }
      else if (source == menuNext) // "Find Next" menu item
      {
        searchFindNext();         // call common method for this operation
      }
      else if (source == menuPasteHex) // "Paste Hex" menu item
      {
        pasteHex();               // call common method for this operation
      }
      else if (source == menuPasteText) // "Paste Text" menu item
      {
        pasteText();              // call common method for this operation
      }
      else if (source == menuReplace) // "Replace" menu item (selection)
      {
        searchReplaceThis();      // call common method for this operation
      }
      else if (source == menuSelect) // "Select All" menu item
      {
        selectAll();              // call common method for this operation
      }
      else if (source == openButton) // "Open File" button
      {
        openFile(null);           // ask for file name, read data from file
        textPanel.beginFile();    // display file from the beginning
      }
      else if (source == overDialog) // insert versus overwrite input mode
      {
        overFlag = overDialog.isSelected(); // transfer GUI to faster boolean
        textPanel.repaint();      // redraw text display as necessary
      }
      else if (source == saveButton) // "Save File" button
      {
        saveFile();               // ask for file name, write data to file
      }
      else if (source == searchCloseButton) // "Close" button on search dialog
      {
        searchDialog.setVisible(false); // hide search dialog box
      }
      else if (source == searchFindButton) // "Find First" button on search
      {
        searchFindFirst();        // call common method for this operation
      }
      else if (source == searchFindText) // press Enter on search text
      {
        searchFindNext();         // call common method for this operation
      }
      else if (source == searchIsText) // user wants text search, not hex
      {
        if (searchIsText.isSelected()) // selecting text search turns on the
          searchByteBound.setSelected(true); // ... byte boundary by default
      }
      else if (source == searchNextButton) // "Find Next" button on search
      {
        searchFindNext();         // call common method for this operation
      }
      else if (source == searchReplaceButton) // "Replace" button on search
      {
        searchReplaceThis();      // call common method for this operation
      }
      else if (source == searchReplaceText) // press Enter on replacement text
      {
        searchReplaceThis();      // call common method for this operation
      }
      else                        // fault in program logic, not by user
      {
        System.err.println("Error in userButton(): unknown ActionEvent: "
          + event);               // should never happen, so write on console
      }
    }
    catch (OutOfMemoryError oome)
    {
      memoryError("userButton");  // nicely tell user that we failed
    }
  } // end of userButton() method

} // end of HexEdit2 class

// ------------------------------------------------------------------------- //

/*
  HexEdit2Data class

  This class maintains an array of byte data.  Data elements are expected to be
  nibbles with values from 0 to 15, although this is not enforced.  Calls are
  similar to the standard Java classes for List and Vector.  The order of the
  parameters and their interpretation may differ.

  A hex editor is a classic compromise between speed and memory requirements.
  The smallest way of storing file data is as a single array of bytes, with one
  array element per file byte.  The fastest way of reading and replacing nibble
  data is as a single array with one element per nibble.  Inserting or deleting
  elements in a single array would require (on average) shuffling half of the
  elements each time.  This is impractical for large file sizes.  The fastest
  way to insert and delete is to have each nibble as a separate element in a
  height-balanced binary tree, which wastes too much space for small elements.

  A rough estimate shows that Java is capable of moving at least 10 million
  bytes per second in a tight <for> loop:

      byte[] second = new byte[first.length];
      for (int i = 0; i < first.length; i ++)
        second[i] = first[i];

  Times were obtained with Java 1.4.2 and Windows 2000 on an Intel Pentium 4
  processor at 2.4 GHz, about five years old by current standards, so most
  other machines have equal or greater performance.  Users will notice GUI
  delays of more than 1/10 of a second.  That means moving much less than a
  megabyte per keystroke.

  In a hex editor, most of the time, the user is only viewing the data.  When
  changes are made, they occur around a cursor location: insert, delete, or
  replace.  The algorithms below assume that it is tolerable to have a small
  delay when first making changes at a new cursor location.  After that, most
  changes will happen without a delay.

  The data is split into two byte arrays of nibble values.  The "left" array
  has data to the left (or before) an imaginary editing cursor.  The "right"
  array has data to the right (or after) this editing cursor.  (The editing
  cursor is derived from the user's real cursor, but tends to lag behind.)
  Backward deletes (the Backspace key) always remove trailing elements from the
  left array.  Forward deletes always remove leading elements from the right
  array.  Inserts go at the end of the left array.  Fetches and replacements go
  to whichever array is appropriate.  When the editing cursor changes, the
  arrays are split at a new location to make changes possible.

  This is neither optimal for speed nor for memory storage.  It is relatively
  fast and easy to implement.  The default Java virtual machine should be able
  to handle files up to 10 megabytes (given the default maximum heap size of 60
  or so megabytes), which is considerably more than the one megabyte that could
  be done previously when each nibble was stored as an element in a Vector
  object.

  The caller can help by always inserting or deleting sequentially from a
  cursor location.  For example, when deleting a byte with the Backspace key
  (backward direction), delete the low-order nibble first, then the high-order
  nibble.  When deleting a byte with the Delete key (forward), delete the high-
  order nibble, then the low-order nibble.
*/

class HexEdit2Data
{
  /* constants */

  static private final int PadSIZE = 4096; // extra space added to arrays

  /* instance variables */

  private byte[] leftArray;       // elements before imaginary editing cursor
  private int leftUsed;           // number of data elements actually used in
                                  // ... <leftArray> starting at index zero
  private byte[] rightArray;      // elements after imaginary editing cursor
  private int rightBegin;         // index of first used element
  private int rightEnd;           // index *after* last used element
  private int totalSize;          // total number of used elements

  /* class constructor */

  public HexEdit2Data(int capacity)
  {
    super();                      // initialize our superclass first (Object)

    if (capacity >= 0)            // initial capacity can't be negative
    {
      leftArray = new byte[PadSIZE]; // create empty left array
      rightArray = new byte[capacity + PadSIZE]; // create empty right array
      leftUsed = rightBegin = rightEnd = totalSize = 0;
    }
    else
    {
      throw new IllegalArgumentException("HexEdit2Data capacity " + capacity
        + " can't be negative");
    }
  }


/*
  append() method

  Append a new element to the end of the data.  Produces the same result as
  inserting or replacing immediately after the last element.
*/
  void append(int value)
  {
    put(size(), value);           // use common processing
  }


/*
  clear() method

  Without allocating new arrays, make them look empty.  All data becomes lost.
  This method is not used anywhere in this program, but is included for a sense
  of completeness.
*/
  void clear()
  {
    leftUsed = rightBegin = rightEnd = totalSize = 0;
  }


/*
  delete() method

  Delete one element at a given location.  Following elements appear to be
  shuffled left, when in fact, they usually don't move: a pointer changes.
*/
  void delete(int position)
  {
    refreshSize();                // refresh total number of data elements
    if ((position < 0) || (position >= totalSize)) // is position within range?
      error(position);            // no, indicate an error
    else if (position == (leftUsed - 1))
      leftUsed --;                // throw away one position on the left
    else if (position == leftUsed)
      rightBegin ++;              // throw away leading position on the right
    else if (position == (totalSize - 1))
      rightEnd --;                // throw away trailing position on the right
    else
    {
      if (split(position))        // break arrays at this position
        rightBegin ++;            // throw away leading position on the right
    }
  } // end of delete() method


/*
  error() method

  Throws an "array index out of bounds" exception.  The caller gives us the
  invalid array index to report.

  The caller should not assume that this terminates processing, because the
  error may be thrown or may later be changed to a System.err.print() call.
*/
  private void error(int position)
  {
    throw new ArrayIndexOutOfBoundsException("HexEdit2Data index " + position
      + " is not from 0 to " + size());
  }


/*
  get() method

  Return the value of an element at a given location, as an unsigned integer.
*/
  int get(int position)
  {
    int result;                   // byte value as unsigned integer

    if ((position < 0) || (position >= size())) // is position within range?
    {
      error(position);            // no, indicate an error
      result = -1;                // in case error() returns to us
    }
    else if (position < leftUsed) // if data can be found in left array
      result = ((int) leftArray[position]) & HexEdit2.BYTE_MASK;
    else                          // otherwise data must be in right array
      result = ((int) rightArray[position - leftUsed + rightBegin])
        & HexEdit2.BYTE_MASK;

    return(result);               // give caller whatever we could find

  } // end of get() method


/*
  insert() method

  Insert a new element at a given location.  The element at that location will
  be shuffled right and will be after the inserted element.  For convenience,
  the location may be immediately after the last element, and is equivalent to
  calling the append() method.
*/
  void insert(int position, int value)
  {
    refreshSize();                // refresh total number of data elements
    if ((position < 0) || (position > totalSize)) // is position within range?
      error(position);            // no, indicate an error
    else if ((position == leftUsed) && (leftUsed < leftArray.length))
      leftArray[leftUsed ++] = (byte) value;
    else if ((position == totalSize) && (rightEnd < rightArray.length))
      rightArray[rightEnd ++] = (byte) value;
    else
    {
      if (split(position))        // break arrays at this position
        leftArray[leftUsed ++] = (byte) value; // insert new element on left
    }
  } // end of insert() method


/*
  put() method

  Replace the element at a given location with a new value.  For convenience,
  the location may be immediately after the last element, and is equivalent to
  calling the append() method.
*/
  void put(int position, int value)
  {
    refreshSize();                // refresh total number of data elements
    if ((position < 0) || (position > totalSize)) // is position within range?
      error(position);            // no, indicate an error
    else if (position < leftUsed) // replace element in left array?
      leftArray[position] = (byte) value;
    else if (position < totalSize) // replace element in right array?
      rightArray[position - leftUsed + rightBegin] = (byte) value;
    else if (rightEnd < rightArray.length) // append element to right array?
      rightArray[rightEnd ++] = (byte) value;
    else
    {
      if (split(position))        // put everything in left array (totalSize)
        leftArray[leftUsed ++] = (byte) value; // insert new element on left
    }
  } // end of put() method


/*
  refreshSize() method

  Internal method to recalculate the total number of data elements used in both
  arrays.  This is an easy but frequently used calculation.
*/
  private void refreshSize()
  {
    totalSize = leftUsed + rightEnd - rightBegin;
  }


/*
  size() method

  Return the total number of data elements.
*/
  int size()
  {
    refreshSize();                // refresh total number of data elements
    return(totalSize);            // and return that value to the caller
  }


/*
  split() method

  Split the left and right arrays at a given location.  Any positions less than
  the given location will be in the left array; positions greater than or equal
  will be in the right array.

  This method is never called frivolously: either the arrays are currently
  divided at the wrong position, or there isn't enough room left for inserting.
  This is the one method that must be very efficient.

  While "out of memory" errors are likely, we don't catch those here.  That is
  done by much higher-level methods in the "main" class that are able to cancel
  entire transactions and properly report to the user.  We just make sure that
  our internal data will be in a consistent state even if an exception occurs
  (that is, work on temporary copies, replace global copies when done).
*/
  private boolean split(int position)
  {
    int count;                    // number of bytes that will be copied
    int from;                     // index where bytes will be copied from
    int i;                        // index variable
    byte[] newLeftArray, newRightArray; // new byte arrays that we are making
    int newLeftUsed, newRightUsed; // number of bytes used in new arrays
    boolean result;               // true if split successful, false otherwise

    refreshSize();                // refresh total number of data elements
    result = false;               // assume that split will fail
    if ((position < 0) || (position > totalSize)) // is position within range?
      error(position);            // no, indicate an error
    else                          // must be a valid split
    {
      /* Copy portion of left array that remains in left array.  The <count>
      variables below may be negative if a situation does not apply. */

      newLeftArray = new byte[position + PadSIZE]; // allocate new left array
      count = Math.min(leftUsed, position); // number of bytes to copy
      newLeftUsed = 0;            // where they go in new left array
      for (i = 0; i < count; i ++)
        newLeftArray[newLeftUsed ++] = leftArray[i];

      /* Copy portion of left array that comes from right array. */

      count = position - leftUsed; // number of bytes to copy
      from = rightBegin;          // where we start copying bytes from
      for (i = 0; i < count; i ++)
        newLeftArray[newLeftUsed ++] = rightArray[from ++];

      /* Copy portion of right array that comes from left array. */

      newRightArray = new byte[totalSize - position + PadSIZE];
      count = leftUsed - position;
      from = position;
      newRightUsed = 0;           // where bytes go in new right array
      for (i = 0; i < count; i ++)
        newRightArray[newRightUsed ++] = leftArray[from ++];

      /* Copy portion of right array that remains in right array. */

      count = totalSize - Math.max(leftUsed, position);
      from = rightEnd - count;
      for (i = 0; i < count; i ++)
        newRightArray[newRightUsed ++] = rightArray[from ++];

      /* Replace existing instance variables with our new variables. */

      leftArray = newLeftArray;
      leftUsed = newLeftUsed;
      rightArray = newRightArray;
      rightBegin = 0;
      rightEnd = newRightUsed;

      result = true;              // declare that split was successful
    }
    return(result);               // tell caller if split was successful

  } // end of split() method

} // end of HexEdit2Data class

// ------------------------------------------------------------------------- //

/*
  HexEdit2Text class

  This class draws the display text area and listens to input from the user.
  The text is monospaced (fixed width) like an old video terminal, as are the
  colors.
*/

class HexEdit2Text extends JPanel
  implements ChangeListener, KeyListener, MouseListener, MouseMotionListener,
    MouseWheelListener
{
  /* constants */

  static final Color ACTIVE_CURSOR = Color.WHITE; // color for active cursor
  static final Color ACTIVE_SELECT = new Color(51, 51, 153); // active selection
  static final int MAX_FONT_SIZE = 73; // maximum font size in points (plus one)
  static final int MIN_FONT_SIZE = 10; // minimum font size in points
  static final Color PANEL_COLOR = Color.BLACK; // panel background color
  static final int PANEL_MARGIN = 10; // outside margin of panel in pixels
  static final Color SHADOW_CURSOR = new Color(102, 102, 102);
                                  // color for mirrored or shadow cursor
  static final Color SHADOW_SELECT = new Color(51, 51, 51);
                                  // color for mirrored or shadow selection
  static final Color TEXT_COLOR = new Color(51, 255, 51); // all displayed text

  /* instance variables */

  int charShifts[];               // pixel offset to center ASCII characters
  int charWidths[];               // pixel width of each ASCII character
  int cursorDot;                  // nibble index for current cursor position
  int cursorMark;                 // nibble index of starting cursor selection
  boolean cursorOnText;           // active cursor: false hex dump, true text
  int lineAscent;                 // number of pixels above baseline
  int lineHeight;                 // height of each display line in pixels
  int maxWidth;                   // maximum pixel width of ASCII characters
  int mousePressNibble;           // mouse pressed: converted nibble index
  boolean mousePressOnText;       // mouse pressed: false hex dump, true text
  int mouseTempNibble;            // mouse temporary: converted nibble index
  boolean mouseTempOnText;        // mouse temporary: false hex dump, true text
  int panelColumns;               // number of complete text columns displayed
  int panelDumpWidth;             // number of input bytes per dump line
  Font panelFont;                 // saved font for drawing text on this panel
  String panelFontName;           // saved font name for <panelFont>
  int panelFontSize;              // saved font size for <panelFont>
  int panelHeight, panelWidth;    // saved panel height and width in pixels
  int panelOffset;                // file offset in bytes for first display row
  int panelRows;                  // number of complete lines (rows) displayed

  /* class constructor */

  public HexEdit2Text()
  {
    super();                      // initialize our superclass first (JPanel)

    /* Set class instance variables to undefined values that we will recognize
    if we are called before the layout and first "paint" is complete. */

    cursorDot = 0;                // nibble index for current cursor position
    cursorMark = 0;               // nibble index of starting cursor selection
    cursorOnText = false;         // assume cursor is active on hex dump
    lineAscent = -1;              // number of pixels above baseline
    lineHeight = -1;              // height of each display line in pixels
    maxWidth = -1;                // maximum pixel width of ASCII characters
    panelColumns = -1;            // number of complete text columns displayed
    panelDumpWidth = -1;          // number of input bytes per dump line
    panelFont = null;             // saved font for drawing text on this panel
    panelFontName = "";           // saved font name for <panelFont>
    panelFontSize = -1;           // saved font size for <panelFont>
    panelHeight = -1;             // saved panel height in pixels
    panelOffset = 0;              // file offset in bytes for first display row
    panelRows = -1;               // number of complete lines (rows) displayed
    panelWidth = -1;              // saved panel width in pixels

    /* Allocate instance arrays.  There is no need to assign initial values. */

    charShifts = new int[HexEdit2.LAST_CHAR + 1]; // use same arrays all fonts
    charWidths = new int[HexEdit2.LAST_CHAR + 1];

    /* Install our keyboard and mouse listeners. */

    this.addKeyListener((KeyListener) this);
    this.addMouseListener((MouseListener) this);
    this.addMouseMotionListener((MouseMotionListener) this);
    this.addMouseWheelListener((MouseWheelListener) this);
//  this.setFocusable(true);      // focus is enabled by default for JPanel
//  this.setFocusTraversalKeysEnabled(false); // we want all keys, even "tab"

  } // end of HexEdit2Text() constructor


/*
  adjustScrollBar() method

  When the window size changes, we need to recalculate several settings for the
  vertical scroll bar.  These are otherwise static except the current position
  and the maximum size (which is subject to insertions and deletions by the
  user).  As a programming note, please call setValues() when setting more than
  one of the parameters, otherwise the change listener may fire between calls
  to the individual methods for setting parameters.
*/
  void adjustScrollBar()
  {
    /* If the dump width increases, our current panel offset can sometimes be
    too large with too much empty space in the display. */

    panelOffset = Math.max(0, Math.min(panelOffset, (((HexEdit2.nibbleCount
      / (panelDumpWidth * 2)) - panelRows + 1) * panelDumpWidth)));
                                  // limit range of panel offset
    panelOffset -= panelOffset % panelDumpWidth;
                                  // truncate or round down to full row

    /* Set values for the scroll bar. */

    HexEdit2.textScroll.setValues((panelOffset / panelDumpWidth), // value
      panelRows,                  // extent (visible amount)
      0,                          // minimum: always zero
      (HexEdit2.nibbleCount / (panelDumpWidth * 2) + 1));
                                  // maximum: round up to next full row

    HexEdit2.textScroll.setBlockIncrement(Math.max(1, (panelRows - 1)));
                                  // dump lines/rows per "scroll one page"
    HexEdit2.textScroll.setUnitIncrement(1); // rows per "scroll one line"

  } // end of adjustScrollBar() method


/*
  beginFile() method

  Display this data file from the beginning, by invalidating any position
  information that we currently have.
*/
  void beginFile()
  {
    cursorDot = 0;                // nibble index for current cursor position
    cursorMark = 0;               // nibble index of starting cursor selection
    cursorOnText = false;         // assume cursor is active on hex dump
    panelDumpWidth = -1;          // forces a complete resize and redraw
    panelOffset = 0;              // file offset of first row in panel display
    repaint();                    // redraw text display as necessary
  }


/*
  convertMouse() method

  Convert a mouse co-ordinate to a data nibble index, along with a flag to say
  if this is on the hex dump region or the ASCII text region.  A negative index
  is an error, although this is not currently used.  Our result has two parts,
  which are saved in temporary class variables.

  We are relaxed about what constitutes a valid mouse location, except for the
  imaginary dividing line between the hex dump and the ASCII text.  Floating-
  point numbers in the code below give some "fuzz" to mouse locations, because
  exact pixel boundaries aren't the best place to accept mouse clicks for text.
*/
  void convertMouse(MouseEvent event)
  {
    int column, row;              // index variables
    int x;                        // adjusted horizontal co-ordinate

    mouseTempOnText = event.getX() > (PANEL_MARGIN + (int)
      ((HexEdit2.OFFSET_DIGITS + (panelDumpWidth * 3) + 2.8) * maxWidth));
                                  // dividing line, with fuzz

    row = (event.getY() - PANEL_MARGIN) / lineHeight; // no fuzzy select for row
    row = Math.max(0, Math.min(row, panelRows)); // allow incomplete final row

    if (mouseTempOnText)          // if mouse is pointing at ASCII text region
    {
      x = event.getX() - PANEL_MARGIN - (int) ((HexEdit2.OFFSET_DIGITS
        + (panelDumpWidth * 3) + 3.8) * maxWidth);
                                  // shifted horizontal for text, with fuzz
      column = (x / maxWidth) * 2; // nibble offset for text
    }
    else                          // mouse is pointing at hex dump or offset
    {
      /* Spaces between pairs of hex digits cause us to first calculate which
      pair we are pointing at in a three-character width, then which nibble
      inside the pair. */

      x = event.getX() - PANEL_MARGIN - (int) ((HexEdit2.OFFSET_DIGITS + 1.5)
        * maxWidth);              // shifted horizontal for pairs, with fuzz
      column = (x / (maxWidth * 3)) * 2;
                                  // nibble offset for high-order digit
      column += ((x % (maxWidth * 3) > (maxWidth * 1.3))) ? 1 : 0;
                                  // increment for low-order digit, some fuzz
    }
    column = Math.max(0, Math.min(column, (panelDumpWidth * 2)));
                                  // limit range of column
    mouseTempNibble = (panelOffset + (row * panelDumpWidth)) * 2 + column;
    mouseTempNibble = Math.min(mouseTempNibble, HexEdit2.nibbleCount);
                                  // selection may include end of nibble data
  } // end of convertMouse() method


/*
  finishArrowKey() method

  All arrow keys (including Page Down and Page Up) have some common processing
  at the end involving selection and making the new cursor visible.  Unlike
  deletions and insertions, we don't need to update the scroll bar sizes.
*/
  void finishArrowKey(KeyEvent event)
  {
    if (event.isShiftDown() == false) // Shift + arrow means extend selection
      cursorMark = cursorDot;     // no selection, so keep cursors together
    limitCursorRange();           // refresh data size, enforce cursor range
    makeVisible(cursorDot);       // make sure that user can see cursor
//  adjustScrollBar();            // adjust scroll bar to match new position
    repaint();                    // redraw text display as necessary
  }


/*
  keyPressed(), keyReleased(), and keyTyped() methods

  These are the keyboard listeners.  They will receive key events as long as
  this component has the focus.  Something that should be noted is that since
  the data bytes/nibbles are contiguous, the end of one line is no different
  than the beginning of the next line.  Do not be surprised when the "End" key
  jumps down one row.  This is not a word processor with cute little paragraph
  markers!
*/
  public void keyPressed(KeyEvent event) { /* not used */ }

  public void keyReleased(KeyEvent event)
  {
    /* Use this method for control keys, that is, keys that don't have a value
    in the Unicode standard.  Ignore anything that we don't want, to avoid
    conflict with the keyTyped() method or other key listeners. */

    try                           // "out of memory" errors are likely
    {
      int key = event.getKeyCode(); // get numeric key code
      switch (key)
      {
        case (KeyEvent.VK_DOWN):  // down arrow
        case (KeyEvent.VK_KP_DOWN): // numeric keypad, down arrow
          if (cursorOnText)       // is active cursor on ASCII text region?
          {
            cursorDot += cursorDot % 2; // force cursor to end of byte
            cursorMark -= cursorMark % 2; // force selection to start of byte
          }
          cursorDot += panelDumpWidth * 2; // go forward one row
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        case (KeyEvent.VK_END):   // "End" key
          if (cursorOnText)       // is active cursor on ASCII text region?
            cursorMark -= cursorMark % 2; // force selection to start of byte
          if (event.isControlDown()) // Control-End means end of the file
            cursorDot = HexEdit2.nibbleCount; // go forward to end-of-file
          else                    // plain End means end of this line/row
          {
            cursorDot += panelDumpWidth * 2; // first go forward one row
            cursorDot -= cursorDot % (panelDumpWidth * 2);
                                  // then go to the beginning of that row
          }
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        case (KeyEvent.VK_F3):    // F3 key for "Find Next" (typical Windows)
          HexEdit2.searchFindNext(); // same as main menu (but not documented)
          break;

        case (KeyEvent.VK_F6):    // F6 key to switch between dump/text regions
          if (event.isShiftDown()) // Shift-F6 is previous panel for Windows
            cursorOnText = false; // here previous is always hex dump region
          else                    // plain F6 is next panel for Windows
            cursorOnText = true;  // here next is always ASCII text region
          limitCursorRange();     // refresh data size, enforce cursor range
          repaint();              // redraw text display as necessary
          break;

        case (KeyEvent.VK_HOME):  // "Home" key
          if (cursorOnText)       // is active cursor on ASCII text region?
            cursorMark += cursorMark % 2; // force selection to end of byte
          if (event.isControlDown()) // Control-Home means begnning of the file
            cursorDot = 0;        // go backward to start-of-file
          else                    // plain Home means start of this line/row
          {
            cursorDot --;         // first go backward one nibble
            cursorDot -= cursorDot % (panelDumpWidth * 2);
                                  // then go to the beginning of that row
          }
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        case (KeyEvent.VK_INSERT): // insert toggles with overwrite
          HexEdit2.overFlag = ! HexEdit2.overFlag; // invert current flag
          HexEdit2.overDialog.setSelected(HexEdit2.overFlag);
                                  // pass change on to GUI dialog box
          repaint();              // redraw text display as necessary
          break;

        case (KeyEvent.VK_LEFT):  // left arrow key
        case (KeyEvent.VK_KP_LEFT): // numeric keypad, left arrow
          if (cursorOnText)       // is active cursor on ASCII text region?
          {
            cursorDot --;         // first go backward one nibble
            cursorDot -= cursorDot % 2; // force cursor to start of byte
            cursorMark += cursorMark % 2; // force selection to end of byte
          }
          else                    // no, must be on hex dump region
            cursorDot --;         // go backward one nibble for hex dump
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        case (KeyEvent.VK_PAGE_DOWN): // "Page Down" key
          if (cursorOnText)       // is active cursor on ASCII text region?
          {
            cursorDot += cursorDot % 2; // force cursor to end of byte
            cursorMark -= cursorMark % 2; // force selection to start of byte
          }
          cursorDot += panelDumpWidth * 2 * Math.max(1, (panelRows - 1));
                                  // go forward one page
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        case (KeyEvent.VK_PAGE_UP): // "Page Up" key
          if (cursorOnText)       // is active cursor on ASCII text region?
          {
            cursorDot -= cursorDot % 2; // force cursor to start of byte
            cursorMark += cursorMark % 2; // force selection to end of byte
          }
          cursorDot -= panelDumpWidth * 2 * Math.max(1, (panelRows - 1));
                                  // go backward one page
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        case (KeyEvent.VK_RIGHT): // right arrow
        case (KeyEvent.VK_KP_RIGHT): // numeric keypad, right arrow
          if (cursorOnText)       // is active cursor on ASCII text region?
          {
            cursorDot ++;         // first go forward one nibble
            cursorDot += cursorDot % 2; // force cursor to end of byte
            cursorMark -= cursorMark % 2; // force selection to start of byte
          }
          else                    // no, must be on hex dump region
            cursorDot ++;         // go forward one nibble for hex dump
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        case (KeyEvent.VK_UP):    // up arrow
        case (KeyEvent.VK_KP_UP): // numeric keypad, up arrow
          if (cursorOnText)       // is active cursor on ASCII text region?
          {
            cursorDot -= cursorDot % 2; // force cursor to start of byte
            cursorMark += cursorMark % 2; // force selection to end of byte
          }
          cursorDot -= panelDumpWidth * 2; // go backward one row
          finishArrowKey(event);  // limit cursor, make visible, repaint
          break;

        default:                  // ignore anything we don't recognize
          break;
      }
    }
    catch (OutOfMemoryError oome)
    {
      HexEdit2.memoryError("keyReleased"); // nicely tell user that we failed
    }
  } // end of keyReleased() method

  public void keyTyped(KeyEvent event)
  {
    /* Accept keys with Unicode values, possibly modified with Alt or Ctrl key
    combinations.  Ignore anything that we don't want, to avoid conflict with
    the keyReleased() method, which does the special "named" keys. */

    try                           // "out of memory" errors are likely
    {
      char ch = event.getKeyChar(); // get Unicode character from keyboard
      if (event.isAltDown())      // menu shortcuts are Alt plus key
      {
        /* Ignore Alt combinations so that we don't interfere with menus. */
      }
      else if (event.isControlDown()) // keyboard shortcuts are Control + key
      {
        switch (ch)
        {
          case (0x01):            // Control-A for "Select All"
            HexEdit2.selectAll(); // same as main menu for "select all"
            break;

          case (0x03):            // Control-C for "Copy"
            if (cursorOnText)     // is active cursor on ASCII text region?
              HexEdit2.copyText(); // yes, same as main menu for "Copy Text"
            else                  // no, must be on hex dump region
              HexEdit2.copyHex(); // same as main menu for "Copy Hex"
            break;

          case (0x06):            // Control-F for "Find"
            HexEdit2.showSearchDialog(); // same as main menu
            break;

          case (0x07):            // Control-G for "Go To File Offset"
            HexEdit2.showGotoDialog(); // same as main menu
            break;

          case (0x0E):            // Control-N for "Find Next"
            HexEdit2.searchFindNext(); // same as main menu
            break;

          case (0x12):            // Control-R for "Replace" (more mnemonic)
            HexEdit2.searchReplaceThis(); // same as main menu
            break;

          case (0x16):            // Control-V for "Paste"
            if (cursorOnText)     // is active cursor on ASCII text region?
              HexEdit2.pasteText(); // yes, same as main menu for "Paste Text"
            else                  // no, must be on hex dump region
              HexEdit2.pasteHex(); // same as main menu for "Paste Hex"
            break;

          case (0x18):            // Control-X for "Cut"
            if (cursorOnText)     // is active cursor on ASCII text region?
              HexEdit2.copyText(); // yes, same as main menu for "Copy Text"
            else                  // no, must be on hex dump region
              HexEdit2.copyHex(); // same as main menu for "Copy Hex"
            HexEdit2.deleteSelected(); // same as main menu for "Delete"
            break;

          case (0x1A):            // Control-Z for "Undo" (not implemented)
            break;

          default:                // ignore anything we don't recognize
            break;
        }
      }
      else if (ch == 0x08)        // Backspace key (separate from Control-H)
      {
        if (cursorDot != cursorMark) // is there a selection?
          HexEdit2.deleteSelected(); // delete current selection and be done
        else if (cursorDot > 0)   // no selection, delete previous byte/nibble
        {
          if (cursorOnText)       // is active cursor on ASCII text (bytes)?
          {
            cursorDot --;         // move backward one nibble
            cursorDot -= cursorDot % 2; // then go backward to start of byte
            if (cursorDot < (HexEdit2.nibbleCount - 1)) // are there 2 nibbles?
              HexEdit2.nibbleData.delete(cursorDot + 1);
                                  // backspace always deletes low-order first
            HexEdit2.nibbleData.delete(cursorDot);
                                  // backspace then deletes high-order nibble
          }
          else                    // no, must be on hex dump region (nibbles)
          {
            cursorDot --;         // move backward one nibble
            HexEdit2.nibbleData.delete(cursorDot); // delete one nibble only
          }
          cursorMark = cursorDot; // both cursors are the same after deletion
          limitCursorRange();     // refresh data size, enforce cursor range
          makeVisible(cursorDot); // make sure that user can see cursor
          adjustScrollBar();      // adjust scroll bar to match new position
          repaint();              // redraw text display as necessary
        }
      }
      else if (ch == 0x1B)        // Escape key
      {
        cursorDot = cursorMark;   // restore original cursor (cancel selection)
        repaint();                // redraw text display as necessary
      }
      else if (ch == 0x7F)        // Delete key
      {
        if (cursorDot != cursorMark) // is there a selection?
          HexEdit2.deleteSelected(); // delete current selection and be done
        else if (cursorDot < HexEdit2.nibbleCount)
                                  // no selection, delete next byte/nibble
        {
          if (cursorOnText)       // is active cursor on ASCII text (bytes)?
          {
            cursorDot -= cursorDot % 2; // go backward to start of byte
            if (cursorDot < (HexEdit2.nibbleCount - 1)) // are there 2 nibbles?
              HexEdit2.nibbleData.delete(cursorDot);
                                  // forward always deletes high-order first
            HexEdit2.nibbleData.delete(cursorDot);
                                  // then delete low-order using same index
          }
          else                    // no, must be on hex dump region (nibbles)
          {
            HexEdit2.nibbleData.delete(cursorDot); // delete one nibble only
          }
          cursorMark = cursorDot; // both cursors are the same after deletion
          limitCursorRange();     // refresh data size, enforce cursor range
          makeVisible(cursorDot); // make sure that user can see cursor
          adjustScrollBar();      // adjust scroll bar to match new position
          repaint();              // redraw text display as necessary
        }
      }
      else if (Character.isISOControl(ch)) // all other control codes
      {
        /* Ignore all control codes that we haven't already recognized. */
      }
      else if (cursorOnText)      // is active cursor on ASCII text region?
      {
        /* Accept all characters that aren't identified as control codes.  To
        be consistent with copy and paste operations, the character is
        converted to a string, the string to a byte array using the system's
        default encoding, and the byte array to nibbles.  As with copy and
        paste, we don't enforce byte boundaries for inserted text. */

        if (HexEdit2.overFlag)    // cancel selection if in overwrite mode
          cursorDot = cursorMark = Math.min(cursorDot, cursorMark);

        byte[] bytes = String.valueOf(ch).getBytes(); // convert to byte array
        int[] nibbles = new int[bytes.length * 2]; // always uses full array
        int used = 0;             // start placing nibbles at this array index
        for (int i = 0; i < bytes.length; i ++) // do all bytes in the string
        {
          nibbles[used ++] = (bytes[i] >> HexEdit2.NIBBLE_SHIFT)
            & HexEdit2.NIBBLE_MASK; // high-order nibble in byte
          nibbles[used ++] = bytes[i] & HexEdit2.NIBBLE_MASK;
                                  // low-order nibble in byte
        }
        HexEdit2.pasteNibbles(nibbles, used, HexEdit2.overFlag);
                                  // paste nibbles as file data
      }
      else                        // no, must be on hex dump region
      {
        /* Accept only hexadecimal digits.  We are called for one character at
        a time, and hence only one digit.  Each digit is one data nibble.  To
        make use of the existing paste methods, we create an array of one
        nibble. */

        if (HexEdit2.overFlag)    // cancel selection if in overwrite mode
          cursorDot = cursorMark = Math.min(cursorDot, cursorMark);

        int hexValue = HexEdit2.charHexValue(ch);
                                  // convert character to value of hex digit
        int[] nibbles = new int[1]; // one nibble if valid input, else zero
        int used = 0;             // will get incremented for valid input

        if (hexValue >= 0)        // was it a valid hexadecimal digit?
          nibbles[used ++] = hexValue; // yes, save the nibble value
        else if (hexValue == HexEdit2.HEX_IGNORE) // ignore space, punctuation?
          { /* do nothing */ }
        else                      // illegal character
          Toolkit.getDefaultToolkit().beep(); // warning sound (may not work)

        if (used > 0)             // was there a valid hexadecimal digit?
          HexEdit2.pasteNibbles(nibbles, used, HexEdit2.overFlag);
                                  // paste nibbles as file data
      }
    }
    catch (OutOfMemoryError oome)
    {
      HexEdit2.memoryError("keyTyped"); // nicely tell user that we failed
    }
  } // end of keyTyped() method


/*
  limitCursorRange() method

  Adjust the cursor "dot" and "mark" to be within the legal range, which is
  from zero to the number of data nibbles.  (The cursor can be after the last
  data nibble.)

  A previous version of this method checked if the cursor was in the ASCII text
  region, and if so, truncated it to a byte boundary, that is, an even number
  of nibbles.  This was not helpful, because the text region couldn't select
  the last nibble if there was an odd number of nibbles after editing.
*/
  void limitCursorRange()
  {
    HexEdit2.refreshDataSize();   // refresh total number of nibbles
    cursorDot = Math.max(0, Math.min(cursorDot, HexEdit2.nibbleCount));
    cursorMark = Math.max(0, Math.min(cursorMark, HexEdit2.nibbleCount));
//  if (cursorOnText)             // is active cursor on ASCII text region?
//  {
//    cursorDot -= cursorDot % 2; // yes, go backward to start of byte
//    cursorMark -= cursorMark % 2;
//  }
  }


/*
  makeVisible() method

  Given a nibble index into the data, check if this nibble is visible in the
  current display.  If not, then adjust the scroll and redraw the display so
  that the nibble appears on the top or bottom row (as appropriate).  This is
  used by all methods that delete, insert, or paste data, or move the cursor
  with keyboard commands.  (The mouse can only select visible positions, except
  in one unusual circumstance.)
*/
  void makeVisible(int nibbleIndex)
  {
    if (nibbleIndex < (panelOffset * 2)) // is nibble before start of display?
    {
      panelOffset = (nibbleIndex / (2 * panelDumpWidth)) * panelDumpWidth;
                                  // put nibble on first display line
      adjustScrollBar();          // adjust scroll bar to match new position
      repaint();                  // redraw text display as necessary
    }
    else if (nibbleIndex >= ((panelOffset + (panelRows * panelDumpWidth)) * 2))
    {
      panelOffset = ((nibbleIndex / (2 * panelDumpWidth)) - panelRows + 1)
        * panelDumpWidth;         // put nibble on last display line
      adjustScrollBar();          // adjust scroll bar to match new position
      repaint();                  // redraw text display as necessary
    }
    else
    {
      /* Nibble is visible.  Do nothing. */
    }
  } // end of makeVisible() method


/*
  mouseClicked() ... mouseReleased() methods

  These are the mouse click and movement listeners.  A plain click sets the
  cursor to the location of the click.  A shift click selects from the cursor
  to the location of the click.  A right click (or control click) shows a menu
  for copy and paste.  A click-and-drag selects from the click to where the
  mouse button is released.

  Mouse clicks are always on the visible display, so we rarely need to scroll.
  We do need to remind Java often that we want the keyboard focus.  Otherwise,
  a menu can open up over this panel, steal the focus, and not return it,
  because the mouse never leaves this panel's region, and Java doesn't call
  mouseEntered() when the menu closes!
*/
  public void mouseClicked(MouseEvent event)
  {
    /* Called when a mouse button has been pressed and released.  Not called at
    the end of a click-and-drag.  Mouse clicks are much less frequent than drag
    events, so we always update the display after a mouse click. */

    requestFocusInWindow();       // request focus, listen for keyboard input
    convertMouse(event);          // convert mouse event to nibble index, type
    if (mouseTempNibble < 0)      // is the mouse position valid for us?
    {
      cursorDot = cursorMark;     // restore original cursor (cancel selection)
      repaint();                  // redraw text display as necessary
    }
    else if (event.isControlDown() // control click means show pop-up menu
      || (event.getButton() != MouseEvent.BUTTON1)) // as does right click
    {
      HexEdit2.showEditMenu(this, event.getX(), event.getY(), true);
    }
    else if (event.isShiftDown()) // shift click means "select to here"
    {
      cursorDot = mouseTempNibble; // select to current mouse position
      cursorOnText = mouseTempOnText; // cursor type: false hex dump, true text
      limitCursorRange();         // refresh data size, enforce cursor range
      makeVisible(cursorDot);     // user may have clicked after last row, last
                                  // ... column, which sets cursor to next page
      repaint();                  // redraw text display as necessary
    }
    else                          // plain mouse click-and-release
    {
      cursorDot = cursorMark = mouseTempNibble; // move cursor to this position
      cursorOnText = mouseTempOnText; // cursor type: false hex dump, true text
      limitCursorRange();         // refresh data size, enforce cursor range
      makeVisible(cursorDot);     // user may have clicked at end of display
      repaint();                  // redraw text display as necessary
    }
  } // end of mouseClicked() method

  public void mouseDragged(MouseEvent event)
  {
    /* Called after a mouse button has been pressed and when the mouse is moved
    before the button is released.  Unlike mouse clicks, mouse drags are called
    many times, so avoid updating the display if nothing has changed. */

    requestFocusInWindow();       // request focus, listen for keyboard input
    convertMouse(event);          // convert mouse event to nibble index, type
    if (mousePressNibble < 0)     // do we have a starting point for drag?
    {
      /* Ignore mouse drag if not starting from valid left button press. */
    }
    else if ((mouseTempNibble < 0) || (mousePressOnText != mouseTempOnText))
    {
      /* Current mouse position is invalid, or the mouse was dragged outside of
      the starting dump or text region. */

      if (cursorDot != cursorMark) // do we really need to update cursor?
      {
        cursorDot = cursorMark;   // restore original cursor (cancel selection)
        repaint();                // redraw text display as necessary
      }
    }
    else                          // update cursor only if it's changed
    {
      boolean updateFlag = false; // true if we need to redraw display
      if (cursorDot != mouseTempNibble) // has cursor position changed?
      {
        cursorDot = mouseTempNibble; // selection now goes to mouse position
        updateFlag = true;        // must redraw display
      }
      if (cursorMark != mousePressNibble) // have we set the selection start?
      {
        cursorMark = mousePressNibble; // button press is now selection start
        updateFlag = true;        // must redraw display
      }
      if (cursorOnText != mouseTempOnText) // where is mouse pointing?
      {
        cursorOnText = mouseTempOnText; // update cursor region from mouse
        updateFlag = true;        // must redraw display
      }
      if (updateFlag)             // do we need to redraw the display?
      {
        /* Don't makeVisible(cursorDot), or else the display will scroll down
        too quickly when the selection ends after the last row and column ...
        and the display never scrolls up for a selection before the first row
        and column, which would be inconsistent behavior. */

        repaint();                // redraw text display as necessary
      }
    }
  } // end of mouseDragged() method

  public void mouseEntered(MouseEvent event)
  {
    requestFocusInWindow();       // request focus, listen for keyboard input
  }

  public void mouseExited(MouseEvent event)
  {
//  transferFocus();              // cancel focus, don't want keyboard input
  }

  public void mouseMoved(MouseEvent event)
  {
    requestFocusInWindow();       // request focus, listen for keyboard input
  }

  public void mousePressed(MouseEvent event)
  {
    /* Called when a mouse button is first pressed.  We don't know if this will
    be a press-and-release or a press-and-drag, so all we can do is to record
    the mouse position as a possible starting point. */

    requestFocusInWindow();       // request focus, listen for keyboard input
    if (event.getButton() == MouseEvent.BUTTON1) // only left click starts drag
    {
      convertMouse(event);        // convert mouse event to nibble index, type
      mousePressNibble = mouseTempNibble; // save converted nibble index
      mousePressOnText = mouseTempOnText; // save region: hex dump, ASCII text
    }
    else                          // ignore drag on all other mouse buttons
      mousePressNibble = -1;      // by refusing to recognize start of drag
  }

  public void mouseReleased(MouseEvent event)
  {
    requestFocusInWindow();       // request focus, listen for keyboard input
  }


/*
  mouseWheelMoved() method

  This is the mouse wheel listener, for the scroll wheel on some mice.  The
  "unit" scroll uses the local system preferences for how many lines/rows per
  click of the mouse.  The "unit" scroll may be too big if there are only one
  or two lines in the display.

  The mouse wheel listener has no interaction with the other mouse listeners
  above.
*/
  public void mouseWheelMoved(MouseWheelEvent event)
  {
    switch (event.getScrollType()) // different mice scroll differently
    {
      case (MouseWheelEvent.WHEEL_BLOCK_SCROLL):
        HexEdit2.textScroll.setValue(HexEdit2.textScroll.getValue()
          + (event.getWheelRotation() * Math.max(1, (panelRows - 1))));
        break;

      case (MouseWheelEvent.WHEEL_UNIT_SCROLL):
        int i = Math.max(1, (panelRows - 1)); // maximum rows to scroll
        i = Math.max((-i), Math.min(i, event.getUnitsToScroll())); // limits
        HexEdit2.textScroll.setValue(HexEdit2.textScroll.getValue() + i);
                                  // scroll using limited local preferences
        break;

      default:                    // ignore anything that we don't recognize
        break;
    }
  } // end of mouseWheelMoved() method


/*
  paintComponent() method

  This is the "paint" method for a Java Swing component.  We have to worry
  about the window size changing, new options chosen by the user, etc.  There
  are many temporary variables in this method, because some calculations are
  difficult and declaring all variables at the beginning would be worse than
  declaring them when they are first used.

  To avoid conflict with the openFile() method, this method always uses the
  current value of <nibbleCount> for the total data size; it *never* calls the
  refreshDataSize() method.  Otherwise this method might try to paint with data
  that has disappeared.  Other GUI events like insert and delete don't have
  the same problem, because they complete before repaint() gets invoked.
*/
  protected void paintComponent(Graphics context)
  {
    int column, row;              // index variables
    int i;                        // index variable

    /* Erase the entire panel region using our choice of background colors. */

    context.setColor(PANEL_COLOR); // flood fill with background color
    context.fillRect(0, 0, this.getWidth(), this.getHeight());

    /* Recalculate panel sizes if any of the following have changed: font name,
    input bytes per dump line, panel height, panel width. */

    if ((HexEdit2.dumpWidth != panelDumpWidth)
      || (HexEdit2.fontName.equals(panelFontName) == false)
      || (this.getWidth() != panelWidth))
    {
      /* We need to find a good font size whenever there is a change to the
      dump width (input bytes per line), font name, or panel width. */

      panelDumpWidth = HexEdit2.dumpWidth; // save current input bytes per line
      panelFontName = HexEdit2.fontName; // save name of current font
      panelHeight = this.getHeight(); // save current panel height in pixels
      panelWidth = this.getWidth(); // save current panel width in pixels

      /* Our search for a font size is intended to shrink or expand lines in
      the dump output so that they fill the width of the panel.  To do this, we
      first need to know how many monospaced text positions are required for
      one complete dump line. */

      panelColumns = HexEdit2.OFFSET_DIGITS // digits in file offset
        + 1                       // space between offset and hex bytes
        + (panelDumpWidth * 3)    // two hex digits per input byte, one space
        + 2                       // two spaces between hex and ASCII text
        + 1                       // left marker for ASCII text
        + panelDumpWidth          // ASCII text corresponding to hex bytes
        + 1;                      // right marker for ASCII text

      /* Look for the biggest font size that doesn't overflow the panel width.
      We do this with a binary search starting at the mid-range of our allowed
      font sizes.  There is not always a smooth progression of pixel widths as
      you increase the point size, and due to rounding errors, we never use the
      supposed maximum size (only the maximum minus one). */

      int fontSizeLow = MIN_FONT_SIZE; // current low end of search range
      int fontSizeHigh = MAX_FONT_SIZE; // current high end of search range
      panelFontSize = -1;         // start without knowing a font size
      while (true)                // this <while> loop ends with a <break>
      {
        int testSize = (fontSizeLow + fontSizeHigh) / 2; // middle of range
        if (panelFontSize == testSize) // has range reduced to one size?
          break;                  // yes, stop looking
        panelFontSize = testSize; // no, calculate widths for this size

        /* Get an instance of the current font in the current test size, and
        grab the widths of all printable ASCII characters. */

        panelFont = new Font(panelFontName, Font.PLAIN, panelFontSize);
        FontMetrics fm = context.getFontMetrics(panelFont);
        lineAscent = fm.getAscent(); // number of pixels above baseline
        lineHeight = fm.getHeight(); // height of each display line in pixels
        maxWidth = -1;            // makes everything else look bigger
        for (i = HexEdit2.FIRST_CHAR; i <= HexEdit2.LAST_CHAR; i ++)
                                  // get widths for printable ASCII characters
        {
          charWidths[i] = fm.charWidth(i); // remember width of each character
          maxWidth = Math.max(charWidths[i], maxWidth); // remember maximum
        }

        /* Adjust search range to increase or decrease the font size.  We stop
        when the search range reduces to one point or less. */

        if ((panelColumns * maxWidth) < (panelWidth - (2 * PANEL_MARGIN)))
          fontSizeLow = panelFontSize; // search same size or larger only
        else
          fontSizeHigh = panelFontSize; // search same size or smaller only
      }

      /* Since not all fonts will be monospaced, calculate how many pixels to
      shift each character right so that it will be centered in the width of
      the widest character. */

      for (i = HexEdit2.FIRST_CHAR; i <= HexEdit2.LAST_CHAR; i ++)
        charShifts[i] = (maxWidth - charWidths[i]) / 2;

      /* Recalculate how many complete rows (lines) of text can be displayed
      inside this panel with the specified margins. */

      panelRows = Math.max(1, ((panelHeight - (2 * PANEL_MARGIN)) / lineHeight));
      adjustScrollBar();          // adjust scroll bar to match new sizes
    }
    else if (this.getHeight() != panelHeight)
    {
      /* If only the panel height has changed, then all we need to do is to
      recalculate how many complete rows (lines) of text can be displayed. */

      panelHeight = this.getHeight(); // save current panel height in pixels
      panelRows = Math.max(1, ((panelHeight - (2 * PANEL_MARGIN)) / lineHeight));
      adjustScrollBar();          // adjust scroll bar to match new sizes
    }

    /* Draw the panel.  All cached screen size, position, and font information
    is correct or has been updated. */

    /* Highlight where selected text will go by redrawing the background color.
    The cursor "dot" and "mark" positions may be in forward or reverse order.
    The region that will be highlighted is irregular (described by up to three
    joined rectangles), so it is easier to highlight pieces individually.  As
    always, avoid repeating the same calculations, in an effort to speed up the
    display. */

    int panelNibbleBegin = panelOffset * 2; // nibble index starting first row
    int panelNibbleEnd = panelNibbleBegin + ((panelRows + 1) * panelDumpWidth
      * 2);                       // after last digit on incomplete final row

    int selectBegin = Math.max(panelNibbleBegin, Math.min(cursorDot,
      cursorMark));               // intersect with display
    int selectEnd = Math.min(panelNibbleEnd, Math.max(cursorDot, cursorMark));
    if (selectBegin < selectEnd)  // only work hard if selection is visible
    {
      /* First draw the selection background for the hex dump. */

      context.setColor(cursorOnText ? SHADOW_SELECT : ACTIVE_SELECT);
                                  // set correct color, if cursor active here

      row = ((selectBegin / 2) - panelOffset) / panelDumpWidth;
                                  // calculate starting row index
      int thisRowY = PANEL_MARGIN + (row * lineHeight);
                                  // convert index to vertical co-ordinate

      int rowNibbleCount = panelDumpWidth * 2; // repeatedly used inside loop
      int nibble = selectBegin % rowNibbleCount;
                                  // calculate nibble index (half byte)
      int rowFirstDumpX = PANEL_MARGIN + maxWidth * (HexEdit2.OFFSET_DIGITS + 2);
                                  // horizontal position first nibble, each row
      int thisColumnX = rowFirstDumpX + (maxWidth * (nibble + (nibble / 2)));
                                  // starting horizontal for first selected

      int nibbleIndex = selectBegin; // start nibbling away at this index
      while (nibbleIndex < selectEnd) // draw one background per each nibble
      {
        context.fillRect(thisColumnX, thisRowY, maxWidth, lineHeight);
        nibble ++;                // move right one nibble on this row
        nibbleIndex ++;           // data index of next nibble
        thisColumnX += maxWidth;  // horizontal co-ordinate for next dump

        if (nibble >= rowNibbleCount) // have we filled one row?
        {
          nibble = 0;             // yes, reset back to beginning of row
          thisColumnX = rowFirstDumpX; // reset horizontal co-ordinate
          thisRowY += lineHeight; // and drop down one row
        }
        else if ((nibbleIndex < selectEnd) && ((nibbleIndex % 2) == 0))
        {
          /* Highlight the space between pairs of nibbles. */

          context.fillRect(thisColumnX, thisRowY, maxWidth, lineHeight);
          thisColumnX += maxWidth; // horizontal co-ordinate for next dump
        }
      }

      /* Second, draw the selection background for the ASCII text.  We don't
      try to be fancy and indicate half a byte if the active cursor is between
      two hex digits in a byte pair (above). */

      context.setColor(cursorOnText ? ACTIVE_SELECT : SHADOW_SELECT);
                                  // set correct color, if cursor active here

      row = ((selectBegin / 2) - panelOffset) / panelDumpWidth;
                                  // calculate starting row index
      thisRowY = PANEL_MARGIN + (row * lineHeight);
                                  // convert index to vertical co-ordinate

      column = (selectBegin % (panelDumpWidth * 2)) / 2;
                                  // calculate column index (full byte)
      int rowFirstTextX = PANEL_MARGIN + maxWidth * (HexEdit2.OFFSET_DIGITS + (3
        * panelDumpWidth) + 4);   // horizontal for first column on each row
      thisColumnX = rowFirstTextX + (maxWidth * column);
                                  // starting horizontal for first selected

      nibbleIndex = selectBegin - (selectBegin % 2);
                                  // round nibble index down to byte boundary

      while (nibbleIndex < selectEnd) // draw one background per two nibbles
      {
        context.fillRect(thisColumnX, thisRowY, maxWidth, lineHeight);
        column ++;                // move right one position
        nibbleIndex += 2;         // next text byte is two nibbles away
        thisColumnX += maxWidth;  // horizontal co-ordinate for next text

        if (column >= panelDumpWidth) // have we filled one row?
        {
          column = 0;             // yes, reset back to beginning of row
          thisColumnX = rowFirstTextX; // reset horizontal co-ordinate
          thisRowY += lineHeight; // and drop down one row
        }
      }
    }

    /* Draw the active cursor (either hex dump or ASCII text) and a mirrored
    shadow cursor (on the opposite side), if visible. */

    if ((cursorDot >= panelNibbleBegin) && (cursorDot < panelNibbleEnd))
    {
      /* First draw the cursor for the hex dump. */

      int cursorY = (cursorDot - panelNibbleBegin) / (panelDumpWidth * 2);
                                  // calculate row index
      cursorY = PANEL_MARGIN + (cursorY * lineHeight);
                                  // convert index to vertical co-ordinate

      int cursorX = (cursorDot % (panelDumpWidth * 2)) / 2;
                                  // calculate column index (full byte)
      cursorX = PANEL_MARGIN + maxWidth * (HexEdit2.OFFSET_DIGITS + (3 * cursorX)
        + 2);                     // convert index to horizontal co-ordinate

      if ((cursorDot % 2) > 0)    // shift right if second nibble in byte
        cursorX += maxWidth;

      context.setColor(cursorOnText ? SHADOW_CURSOR : ACTIVE_CURSOR); // active?
      if (HexEdit2.overFlag)      // is this an overwrite cursor (box)?
      {
        context.drawRect((cursorX - 1), cursorY, (maxWidth + 1),
          (lineHeight - 1));      // regular thin cursor (one pixel width)
        if (panelFontSize > 24)   // big fonts need a fatter cursor
          context.drawRect(cursorX, (cursorY + 1), (maxWidth - 1),
            (lineHeight - 3));    // additional one-pixel box inside first
      }
      else                        // must be an insert cursor (vertical line)
        context.fillRect((cursorX - 1), cursorY,
          ((panelFontSize > 24) ? 3 : 2), lineHeight);

      /* Second, draw the cursor for the ASCII text.  Use the same <cursorY> as
      above.  We don't try to be fancy and indicate half a byte if the active
      cursor is between two hex digits in a byte pair (above). */

      cursorX = (cursorDot % (panelDumpWidth * 2)) / 2;
                                  // calculate column index (full byte)
      cursorX = PANEL_MARGIN + maxWidth * (HexEdit2.OFFSET_DIGITS + (3
        * panelDumpWidth) + cursorX + 4);
                                  // convert index to horizontal co-ordinate

      context.setColor(cursorOnText ? ACTIVE_CURSOR : SHADOW_CURSOR); // active?
      if (HexEdit2.overFlag)      // is this an overwrite cursor (box)?
      {
        context.drawRect((cursorX - 1), cursorY, (maxWidth + 1),
          (lineHeight - 1));      // regular thin cursor (one pixel width)
        if (panelFontSize > 24)   // big fonts need a fatter cursor
          context.drawRect(cursorX, (cursorY + 1), (maxWidth - 1),
            (lineHeight - 3));    // additional one-pixel box inside first
      }
      else                        // must be an insert cursor (vertical line)
        context.fillRect((cursorX - 1), cursorY,
          ((panelFontSize > 24) ? 3 : 2), lineHeight);
    }

    /* All text on the panel has the same color and font.  Only the background
    color changes, or the addition of a cursor (above). */

    context.setColor(TEXT_COLOR);
    context.setFont(panelFont);

    /* Draw the file byte offsets on the left side and the ASCII text markers
    on the right side.  Although the overall difference in speed may be small,
    avoid recalculating screen positions, such as multiplications and
    especially divisions.  Allow for one more nibble than is in the file, so
    the user has somewhere to insert text at the end of the file.  There is
    always at least one file offset, even if the file is empty. */

    int maxOffset = HexEdit2.nibbleCount / 2;
                                  // don't display offsets past this value
    int rowLastDigitX = PANEL_MARGIN + (HexEdit2.OFFSET_DIGITS - 1) * maxWidth;
                                  // all rows put low-order offset digit here
    int rowLeftMarkerX = PANEL_MARGIN + maxWidth * ((HexEdit2.OFFSET_DIGITS + 3
      + (3 * panelDumpWidth))) + charShifts[HexEdit2.MARKER_CHAR];
                                  // all rows put left text marker here
    int rowRightMarkerX = rowLeftMarkerX + maxWidth * (panelDumpWidth + 1);
                                  // all rows put right text marker here
    int rowY = PANEL_MARGIN + lineAscent; // vertical baseline for first row
    int thisOffset = panelOffset; // byte offset for first row

    for (row = 0; row <= panelRows; row ++) // allow incomplete final row
    {
      if (thisOffset > maxOffset) // have we gone too far? (">" is correct)
        break;                    // yes, escape early from <for> loop
      int shiftedOffset = thisOffset; // copy offset so as to extract digits
      int thisDigitX = rowLastDigitX; // horizontal start for low-order digit
      for (i = HexEdit2.OFFSET_DIGITS; i > 0; i --)
                                  // extract digits from low-order end
      {
        char ch = HexEdit2.HEX_DIGITS[shiftedOffset & HexEdit2.NIBBLE_MASK];
                                  // convert nibble to hex text digit
        context.drawString(Character.toString(ch), (thisDigitX
          + charShifts[ch]), rowY); // center this character onto the screen
        shiftedOffset = shiftedOffset >> HexEdit2.NIBBLE_SHIFT;
                                  // shift down next higher-order nibble
        thisDigitX -= maxWidth;   // back up for next higher-order digit
      }
      context.drawString(HexEdit2.MARKER_STRING, rowLeftMarkerX, rowY);
                                  // left text marker with centering shift
      context.drawString(HexEdit2.MARKER_STRING, rowRightMarkerX, rowY);
                                  // right text marker with centering shift
      rowY += lineHeight;         // vertical position for next row
      thisOffset += panelDumpWidth; // byte offset for next row
    }

    /* Draw the hex dump and plain text ASCII characters.  As before, avoid
    unnecessary calculations.  This can be entirely empty if the file is empty,
    or if the display is only big enough for one row and we are at the end of a
    file that exactly filled the previous row. */

    int nibbleIndex = panelOffset * 2; // index of first data nibble, first row
    int rowFirstDumpX = PANEL_MARGIN + maxWidth * (HexEdit2.OFFSET_DIGITS + 2);
                                  // horizontal position first nibble, each row
    int rowFirstTextX = rowFirstDumpX + maxWidth * ((3 * panelDumpWidth) + 2);
                                  // horizontal position first text, each row
    rowY = PANEL_MARGIN + lineAscent; // vertical baseline for first row

    for (row = 0; row <= panelRows; row ++) // allow incomplete final row
    {
      int thisDumpX = rowFirstDumpX; // horizontal dump start for this row
      int thisTextX = rowFirstTextX; // horizontal text start for this row
      for (column = 0; column < panelDumpWidth; column ++)
      {
        /* first nibble of two */

        if (nibbleIndex >= HexEdit2.nibbleCount) // have we gone too far?
          break;                  // yes, escape early from <for> loop
        int thisNibble = HexEdit2.nibbleData.get(nibbleIndex ++);
                                  // get value of this nibble as integer
        char ch = HexEdit2.HEX_DIGITS[thisNibble & HexEdit2.NIBBLE_MASK];
                                  // convert nibble to hex text digit
        context.drawString(Character.toString(ch), (thisDumpX
          + charShifts[ch]), rowY); // center this hex digit onto screen
        thisDumpX += maxWidth;    // horizontal position for next hex digit

        int byteValue = thisNibble << HexEdit2.NIBBLE_SHIFT;
                                  // first nibble is high-order part of byte

        /* second nibble of two (may be missing) */

        if (nibbleIndex < HexEdit2.nibbleCount) // is there a second nibble?
        {
          thisNibble = HexEdit2.nibbleData.get(nibbleIndex ++);
                                  // get value of this nibble as integer
          ch = HexEdit2.HEX_DIGITS[thisNibble & HexEdit2.NIBBLE_MASK];
                                  // convert nibble to hex text digit
          context.drawString(Character.toString(ch), (thisDumpX
            + charShifts[ch]), rowY); // center this hex digit onto screen
          thisDumpX += maxWidth;  // horizontal position for next hex digit

          byteValue |= thisNibble; // insert second nibble into byte value
        }
        else
          byteValue = -1;         // don't try to display incomplete bytes

        thisDumpX += maxWidth;    // skip space between pairs of hex digits

        /* text character (if printable) */

        if ((byteValue < HexEdit2.FIRST_CHAR) || (byteValue > HexEdit2.LAST_CHAR))
          byteValue = HexEdit2.REPLACE_CHAR; // replace unprintable character
        context.drawString(Character.toString((char) byteValue), (thisTextX
          + charShifts[byteValue]), rowY); // draw character onto screen
        thisTextX += maxWidth;    // horizontal position for next text char
      }
      rowY += lineHeight;         // vertical position for next row
    }
  } // end of paintComponent() method


/*
  stateChanged() method

  Currently only used for the vertical scroll bar.  This method gets called
  often, perhaps too often.  Try to invoke other methods only if something
  important has changed.
*/
  public void stateChanged(ChangeEvent event)
  {
    if (panelDumpWidth > 1)       // are we ready to handle this yet?
    {
      int scroll = HexEdit2.textScroll.getValue(); // scroll bar row position
      int newOffset = scroll * panelDumpWidth; // convert rows to input bytes
      if (newOffset != panelOffset) // has drawing position truly changed?
      {
        panelOffset = newOffset;  // yes, remember new starting offset
        repaint();                // redraw text display as necessary
      }
    }
  } // end of stateChanged() method

} // end of HexEdit2Text class

// ------------------------------------------------------------------------- //

/*
  HexEdit2User class

  This class listens to input from the user and passes back event parameters to
  static methods in the main class.
*/

class HexEdit2User implements ActionListener, Transferable
{
  /* empty constructor */

  public HexEdit2User() { }

  /* button listener, dialog boxes, etc */

  public void actionPerformed(ActionEvent event)
  {
    HexEdit2.userButton(event);
  }

  /* clipboard data transfer */

  public Object getTransferData(DataFlavor flavor)
    throws IOException, UnsupportedFlavorException
  {
    if (HexEdit2.clipString == null)
      throw new IOException("no clipboard string created");
    else if (flavor.equals(DataFlavor.stringFlavor))
      return(HexEdit2.clipString);
    else
      throw new UnsupportedFlavorException(flavor);
  }

  public DataFlavor[] getTransferDataFlavors()
  {
    final DataFlavor[] result = { DataFlavor.stringFlavor };
    return(result);
  }

  public boolean isDataFlavorSupported(DataFlavor flavor)
  {
    return(flavor.equals(DataFlavor.stringFlavor));
  }

} // end of HexEdit2User class

/* Copyright (c) 2008 by Keith Fenske.  Apache License or GNU GPL. */
