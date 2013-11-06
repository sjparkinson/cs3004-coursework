package Client.UserInterface;

import Client.ReportClient;
import Framework.Logger.ReportLogger;
import Framework.ObservationResult.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 06/11/13
 * Time: 09:49
 */
public class ReportClientInterface
{
    private JPanel MainPanel;
    private JTextField textName;
    private JComboBox comboDobDay;
    private JComboBox comboDobMonth;
    private JComboBox comboDobYear;
    private JLabel DateOfBirthLabel;
    private JRadioButton radioGenderMale;
    private JRadioButton radioGenderFemale;
    private JComboBox comboObservationType;
    private JTextField textObservationValue;
    private JTextField textObservationUnits;
    private JButton sendButton;
    private ButtonGroup GenderRadioSelection;

    private PatientGender selectedGender;
    private ObservationType selectedObservationType;
    private DateTime selectedDateOfBirth;

    private static final ReportLogger Log = ReportLogger.getLogger();


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame("ReportClientInterface");
        frame.setContentPane(new ReportClientInterface().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public ReportClientInterface()
    {
        setDobYearItems();

        setObservationTypeItems();

        getSelectionValues();

        sendButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Log.debug("Send button pressed.");

                final ObservationResult message = new ObservationResult();

                message.Patient = new Patient(
                        textName.getText(),
                        selectedDateOfBirth,
                        selectedGender);

                message.Observation = new Observation(
                        selectedObservationType,
                        textObservationValue.getText(),
                        textObservationUnits.getText());

                Runnable communicationWorker = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        ReportClient.sendObservation(message);
                    }
                };

                Thread communicationThread = new Thread(communicationWorker);

                communicationThread.start();
            }
        });
    }

    private void getSelectionValues()
    {
        ActionListener dobListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DateTimeFormatter format = DateTimeFormat.forPattern("MMM");

                DateTime monthInstance = format.parseDateTime(comboDobMonth.getSelectedItem().toString());

                selectedDateOfBirth = new DateTime(
                        getIntValue(comboDobYear),
                        monthInstance.getMonthOfYear(),
                        getIntValue(comboDobDay),
                        0,
                        0
                );
            }

            private int getIntValue(JComboBox comboBox)
            {
                return Integer.parseInt(comboBox.getSelectedItem().toString());
            }
        };

        comboDobDay.addActionListener(dobListener);
        comboDobMonth.addActionListener(dobListener);
        comboDobYear.addActionListener(dobListener);

        ActionListener genderListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (radioGenderFemale.isSelected())
                {
                    selectedGender = PatientGender.Female;
                }
                else
                {
                    selectedGender = PatientGender.Male;
                }
            }
        };

        radioGenderMale.addActionListener(genderListener);
        radioGenderFemale.addActionListener(genderListener);

        comboObservationType.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedObservationType = ObservationType.fromString(comboObservationType.getSelectedItem().toString());
            }
        });
    }

    private void setObservationTypeItems()
    {
        Vector comboObservationTypeItems = new Vector();

        for (ObservationType type : ObservationType.values())
        {
            if (type != ObservationType.None)
            {
                comboObservationTypeItems.add(type);
            }
        }

        final DefaultComboBoxModel comboTypeModel = new DefaultComboBoxModel(comboObservationTypeItems);

        comboObservationType.setModel(comboTypeModel);
    }

    private void setDobYearItems()
    {
        Vector comboDobYearItems = new Vector();

        // set years
        for (int i = 1898; i <= new DateTime().getYear(); i++)
        {
            comboDobYearItems.add(i);
        }

        final DefaultComboBoxModel comboDobModel = new DefaultComboBoxModel(comboDobYearItems);

        comboDobYear.setModel(comboDobModel);
    }
}
