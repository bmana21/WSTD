import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Window extends JFrame {
    public JTextArea textBox1;
    public JButton button2, button1;

    void InitializeComponent() {
        textBox1 = new JTextArea(100, 70);
        textBox1.setBackground(Color.WHITE);
        textBox1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(textBox1, BorderLayout.CENTER);
        button1 = new JButton("Start");
        button2 = new JButton("Clear");
        button1.setBackground(new Color(250, 250, 250));
        button2.setBackground(new Color(250, 250, 250));
        JPanel panel = new JPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        panel.setLayout(new GridBagLayout());
        panel.add(button1, constraints);
        constraints.gridy = 1;
        panel.add(button2, constraints);
        button1.setPreferredSize(new Dimension(120, 100));
        button2.setPreferredSize(new Dimension(120, 100));
        button1.setBorderPainted(false);
        button2.setBorderPainted(false);
        panel.setPreferredSize(new Dimension(120, Integer.MAX_VALUE));
        add(panel, BorderLayout.EAST);
        panel.setBackground(Color.CYAN);
        button1.addActionListener(actionEvent -> button1_Click());
        button2.addActionListener(actionEvent -> button2_Click());
    }

    public Window() {
        super("Codeforces");
        setSize(500, 500);
        getContentPane().setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        InitializeComponent();
        setVisible(true);
    }

    int u = 11111111;

    public String random() {
        String pas = "a" + u;
        u++;
        return pas;
    }

    String def = "#define", inc = "#include";

    private <E> ArrayList<E> shuffleList(ArrayList<E> inputList) {
        ArrayList<E> randomList = new ArrayList<>();

        Random r = new Random();
        int randomIndex;
        while (inputList.size() > 0) {
            randomIndex = r.nextInt(inputList.size());
            randomList.add(inputList.get(randomIndex));
            inputList.remove(randomIndex);
        }

        return randomList;
    }

    int com = 0;
    String pas = "";

    private void button1_Click() {
        u = 11111111;
        int n = 1;
        pas = "";
        String[] s = new String[10000];
        HashMap<String, String> mp = new HashMap<>();
        HashMap<String, String> pm = new HashMap<>();
        HashMap<String, Integer> used = new HashMap<>();
        ArrayList<String> v = new ArrayList<>(), only = new ArrayList<>(), fori = new ArrayList<>();
        String text = textBox1.getText();
        String[] lines = text.split("\n");
        for (String line : lines) {
            s[n] = line;
            s[n] = s[n].trim();
            if (s[n].length() == 0 || (s[n].length() >= 2 && s[n].charAt(0) == '/' && s[n].charAt(1) == '/'))
                continue;
            if (s[n].length() >= 2 && s[n].charAt(0) == '/' && s[n].charAt(1) == '*')
                com = 1;
            if (com == 0) {
                if (s[n].length() >= def.length()) {
                    int ind = 0;
                    for (int pp = 0; pp < def.length(); pp++) {
                        if (s[n].charAt(pp) != def.charAt(pp)) {
                            ind = 1;
                            break;
                        }
                    }
                    if (ind == 0) {
                        only.add(s[n]);
                        n++;
                        continue;
                    }

                }
                if (s[n].length() >= inc.length()) {
                    int ind = 0;
                    for (int pp = 0; pp < inc.length(); pp++) {
                        if (s[n].charAt(pp) != inc.charAt(pp)) {
                            ind = 1;
                            break;
                        }
                    }
                    if (ind == 0) {
                        fori.add(s[n]);
                        n++;
                        continue;
                    }
                }

                String o = random();
                if (used.containsKey(s[n]))
                    if (used.get(s[n]) == 1) {
                        n++;
                        continue;
                    }

                mp.put(s[n], o);
                pm.put(o, s[n]);
                used.put(s[n], 1);
                v.add(o);
                n++;
            }
            if (com == 1)
                if (s[n].length() >= 2)
                    if (s[n].charAt(s[n].length() - 1) == '/' && s[n].charAt(s[n].length() - 2) == '*')
                        com = 0;

        }
        for (String value : fori) {
            pas += value + "\n";
        }
        for (String value : only) {
            pas += value + "\n";
        }
        v = shuffleList(v);
        for (String value : v) {
            pas += "#define " + value + " " + pm.get(value) + "\n";
        }
        for (int k = 1; k < n; k++) {
            if (used.containsKey(s[k]))
                pas += mp.get(s[k]) + "\n";
        }
        if (!pas.equals("")) {
            StringSelection selection = new StringSelection(pas);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }
    }

    private void button2_Click() {
        u = 11111111;
        com = 0;
        textBox1.setText("");
        pas = "";
    }


}