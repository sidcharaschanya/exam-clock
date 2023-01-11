import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;

public class ExamClock extends JFrame implements ActionListener {
    JTextField txtSubject, txtHours, txtMinutes, txtSeconds;
    JButton btnStart, btnStop, btnReset, btnAddSubject;
    Font fntSubject, fntTime, fntButton;
    Container container;
    JPanel pnlTime, pnlButtons;
    Timer timer;

    private static int examClockCount = 1;

    ExamClock() {
        fntSubject = new Font("Courier", Font.PLAIN, 30);
        fntTime = new Font("Courier", Font.PLAIN, 80);
        fntButton = new Font("Courier", Font.PLAIN, 15);

        txtSubject = new JTextField("Subject Name");
        txtSubject.setHorizontalAlignment(JTextField.CENTER);
        txtSubject.setFont(fntSubject);

        txtHours = new JTextField("HH");

        txtMinutes = new JTextField("MM");

        txtSeconds = new JTextField("SS");

        txtHours.setHorizontalAlignment(JTextField.CENTER);
        txtMinutes.setHorizontalAlignment(JTextField.CENTER);
        txtSeconds.setHorizontalAlignment(JTextField.CENTER);

        txtHours.setFont(fntTime);
        txtMinutes.setFont(fntTime);
        txtSeconds.setFont(fntTime);

        btnStart = new JButton("Start");

        btnStop = new JButton("Stop");
        btnStop.setEnabled(false);

        btnReset = new JButton("Reset");

        btnAddSubject = new JButton("Add Subject");

        btnStart.setFont(fntButton);
        btnStop.setFont(fntButton);
        btnReset.setFont(fntButton);
        btnAddSubject.setFont(fntButton);

        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        btnReset.addActionListener(this);
        btnAddSubject.addActionListener(this);

        container = this.getContentPane();
        container.setLayout(new BorderLayout());

        pnlTime = new JPanel(new GridLayout(1, 3));

        pnlButtons = new JPanel(new GridLayout(1, 4));

        pnlTime.add(txtHours);
        pnlTime.add(txtMinutes);
        pnlTime.add(txtSeconds);

        pnlButtons.add(btnStart);
        pnlButtons.add(btnStop);
        pnlButtons.add(btnReset);
        pnlButtons.add(btnAddSubject);

        container.add(txtSubject, BorderLayout.NORTH);
        container.add(pnlTime, BorderLayout.CENTER);
        container.add(pnlButtons, BorderLayout.SOUTH);

        this.setTitle("Exam Clock " + examClockCount);
        this.setSize(600, 400);
        this.setMinimumSize(new Dimension(600, 400));
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            startTimer();
        }
        if (e.getSource() == btnStop) {
            stopTimer();
        }
        if (e.getSource() == btnReset) {
            reset();
        }
        if (e.getSource() == btnAddSubject) {
            addSubject();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new Countdown(this), 0, 1000);

        txtSubject.setEnabled(false);
        txtHours.setEnabled(false);
        txtMinutes.setEnabled(false);
        txtSeconds.setEnabled(false);

        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        btnReset.setEnabled(false);
    }

    private void stopTimer() {
        timer.cancel();

        txtSubject.setEnabled(true);
        txtHours.setEnabled(true);
        txtMinutes.setEnabled(true);
        txtSeconds.setEnabled(true);

        txtHours.setBackground(Color.WHITE);
        txtMinutes.setBackground(Color.WHITE);
        txtSeconds.setBackground(Color.WHITE);

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        btnReset.setEnabled(true);
    }

    private void reset() {
        txtSubject.setText("Subject Name");
        txtHours.setText("HH");
        txtMinutes.setText("MM");
        txtSeconds.setText("SS");
    }

    private void addSubject() {
        examClockCount++;
        new ExamClock();
    }

    public static void main(String[] args) {
        new ExamClock();
    }
}

class Countdown extends TimerTask {
    private final ExamClock EXAM_CLOCK;

    private int time;

    Countdown(ExamClock examClock) {
        EXAM_CLOCK = examClock;

        int initialSeconds = Integer.parseInt(EXAM_CLOCK.txtSeconds.getText());
        int initialMinutes = Integer.parseInt(EXAM_CLOCK.txtMinutes.getText()) * 60;
        int initialHours = Integer.parseInt(EXAM_CLOCK.txtHours.getText()) * 3600;

        time = initialSeconds + initialMinutes + initialHours;
    }

    public void run() {
        int nextHours = time / 3600;
        int nextMinutes = time / 60 - nextHours * 60;
        int nextSeconds = time - nextHours * 3600 - nextMinutes * 60;

        String hours = Integer.toString(nextHours);
        String minutes = Integer.toString(nextMinutes);
        String seconds = Integer.toString(nextSeconds);

        if (hours.length() == 1) {
            hours = "0" + hours;
        }
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        EXAM_CLOCK.txtHours.setText(hours);
        EXAM_CLOCK.txtMinutes.setText(minutes);
        EXAM_CLOCK.txtSeconds.setText(seconds);

        time--;

        if (nextHours == 0 && nextMinutes == 0 && nextSeconds == 0) {
            EXAM_CLOCK.txtHours.setBackground(Color.GREEN);
            EXAM_CLOCK.txtMinutes.setBackground(Color.GREEN);
            EXAM_CLOCK.txtSeconds.setBackground(Color.GREEN);
            this.cancel();
        }
    }
}
