package com.dangoxj.utils;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Created by zhangshouzhi on 13-12-1.
*/
public class Frame extends JFrame {

    private static final long serialVersionUID = 5584323488719218808L;
    private JLabel jLabelProduct = null;
    private JLabel jLabelVersion = null;
    private JComboBox jComboBoxProduct = null;
    private JComboBox jComboBoxVersion = null;
    private JLabel jLabelInputPath = null;
    private JTextField jtFieldInputPath = null;
    private JButton jButtonInput = null;
    private JLabel jLabelOutputPath = null;
    private JTextField jtFieldOutputPath = null;
    private JLabel jLabelESN = null;
    private JTextArea jtAreaESN = null;
    private JScrollPane jsPaneESN = null;

    private JButton jButtonOutput = null;
    private JCheckBox jCheckBoxAgree = null;

    private JButton jButtonCommit = null;

    private JProgressBar jProgressBar = null;

    public void init() {
        setTitle("Title");
        setLayout(null);
        setBounds(0, 0, 600, 480);

        Container container = this.getContentPane();

        jLabelProduct = new JLabel();
        jLabelProduct.setText("≤˙∆∑:");
        jLabelProduct.setBounds(30, 20, 50, 32);
        container.add(jLabelProduct);

        String usn[] = {"USN9810"};
        jComboBoxProduct = new JComboBox(usn);
        jComboBoxProduct.setSelectedIndex(0);
        jComboBoxProduct.setBounds(80, 20, 120, 32);
        container.add(jComboBoxProduct);

        jLabelVersion = new JLabel();
        jLabelVersion.setText("∞Ê±æ:");
        jLabelVersion.setBounds(30,60,50,32);
        container.add(jLabelVersion);

        String version[] = {"R10","R11","R12"};
        jComboBoxVersion = new JComboBox(version);
        jComboBoxVersion.setBounds(80, 60, 120, 32);
        jComboBoxVersion.setSelectedIndex(2);
        container.add(jComboBoxVersion);

        jLabelESN = new JLabel();
        jLabelESN.setText("ESN:");
        jLabelESN.setBounds(250, 20, 100, 32);
        container.add(jLabelESN);

        jtAreaESN = new JTextArea();
//		jtAreaESN.setBounds(30, 250, 400, 64);
        jtAreaESN.setAutoscrolls(true);
        jtAreaESN.setLineWrap(true);
        jsPaneESN =new JScrollPane(jtAreaESN);
        jsPaneESN.setBounds(290, 25, 255, 64);
        container.add(jsPaneESN);

        jLabelInputPath = new JLabel();
        jLabelInputPath.setText("ƒ£∞Â¬∑æ∂:");
        jLabelInputPath.setBounds(30, 100, 100, 32);
        container.add(jLabelInputPath);

        jtFieldInputPath = new JTextField();
        jtFieldInputPath.setText("/Users/zhangshouzhi/Documents/workspace/");
        jtFieldInputPath.setBounds(100, 100, 400, 32);
        container.add(jtFieldInputPath);

        jButtonInput = new JButton();
        jButtonInput.setText("—°‘Ò");
        jButtonInput.setBounds(500, 100, 50, 32);
        jButtonInput.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser jfChooser = new JFileChooser();
                jfChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = jfChooser.showOpenDialog(Frame.this);
                if ( ret == JFileChooser.APPROVE_OPTION ) {
                    jtFieldInputPath.setText(jfChooser.getSelectedFile().getPath());
                }
            }

        });
        container.add(jButtonInput);


        jLabelOutputPath = new JLabel();
        jLabelOutputPath.setText(" ‰≥ˆ¬∑æ∂:");
        jLabelOutputPath.setBounds(30, 150, 100, 32);
        container.add(jLabelOutputPath);

        jtFieldOutputPath = new JTextField();
        jtFieldOutputPath.setText("");
        jtFieldOutputPath.setBounds(100, 150, 400, 32);
        container.add(jtFieldOutputPath);

        jButtonOutput = new JButton();
        jButtonOutput.setText("—°‘Ò");
        jButtonOutput.setBounds(500, 150, 50, 32);
        jButtonOutput.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser jfChooser = new JFileChooser();
                jfChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = jfChooser.showOpenDialog(Frame.this);
                if ( ret == JFileChooser.APPROVE_OPTION ) {
                    jtFieldOutputPath.setText(jfChooser.getSelectedFile().getPath());
                }
            }

        });
        container.add(jButtonOutput);

        jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL );
        jProgressBar.setStringPainted(true);
        jProgressBar.setVisible(true);
        jProgressBar.setBounds(30, 200, 400, 16);
        container.add(jProgressBar);

        jCheckBoxAgree = new JCheckBox("Œ“≥–≈µ°£°£°£");
        jCheckBoxAgree.setSelected(false);
        jCheckBoxAgree.setBounds(30, 350, 240, 32);
        jCheckBoxAgree.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent event) {
                jButtonCommit.setEnabled(jCheckBoxAgree.isSelected());
            }

        });
        container.add(jCheckBoxAgree);

        jButtonCommit = new JButton();
        jButtonCommit.setText("ø™ º…Í«Î");
        jButtonCommit.setBounds(300, 350, 100, 32);
        jButtonCommit.setEnabled(false);
        jButtonCommit.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        new Thread(){

                            public void run() {

                                Logger logger  =  Logger.getLogger(XML.class );
                                logger.addAppender(new JTextAreaAppender(Frame.this.jtAreaESN));

                                logger.debug( " debug " );
                                logger.error( " error " );

                                logger.warn("Low fuel level.");

                                // This request is disabled, because DEBUG < INFO.
                                logger.debug("Starting search for nearest gas station.");

                                // The logger instance barlogger, named "com.foo.Bar",
                                // will inherit its level from the logger named
                                // "com.foo" Thus, the following request is enabled
                                // because INFO >= INFO.
                                logger.info("Located nearest gas station.");

                                // This request is disabled, because DEBUG < INFO.
                                logger.debug("Exiting gas station search");

                            }

                        }.start();
                    }

                });
        container.add(jButtonCommit);


    }

    public void showMyself(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ¥∞ÃÂ¥Û–°≤ªƒ‹∏ƒ±‰
        setResizable(false);
        // æ”÷–œ‘ æ
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

