import java.util.LinkedList;
import java.util.List;

public class Motor
{
    private static List<Motor> systemMotors = new LinkedList<Motor>();

    private String name;
    private List<Motor> inputs = new LinkedList<Motor>();
    private List<Motor> outputs = new LinkedList<Motor>();
    private MotorStatus status = MotorStatus.CLEAN_STOP;

    public Motor(String _name)
    {
        name = _name;
        systemMotors.add(this);
        addOutputs();
    }

    public Motor(String _name, Motor inOne)
    {
        this(_name);
        inputs.add(inOne);
        addOutputs();
    }

    public Motor(String _name, Motor inOne, Motor inTwo)
    {
        this(_name);
        inputs.add(inOne);
        inputs.add(inTwo);
        addOutputs();
    }

    public Motor(String _name, List<Motor> inMotors)
    {
        this(_name);

        for(int i = 0; i < inMotors.size(); i++)
        {
            inputs.add(inMotors.get(i));
        }
        
        addOutputs();
    }

    private void addOutputs()
    {
        // add this motor to it's input motors' output list
        for(int i = 0; i < inputs.size(); i++)
        {
            inputs.get(i).getOutputs().add(this);
        }

        // populate this motor's output list
        for(int i = 0; i < Motor.systemMotors.size(); i++)
        {
            for(int j = 0; j < Motor.systemMotors.get(i).getInputs().size(); j++)
            {
                if(Motor.systemMotors.get(i).getInputs().get(j) == this)
                {
                    outputs.add(Motor.systemMotors.get(i));
                }
            }
        }  
    }

    public String getName()
    {
        return name;
    }

    public MotorStatus getStatus()
    {
        return status;
    }

    public List<Motor> getInputs()
    {
        return inputs;
    }

    public List<Motor> getOutputs()
    {
        return outputs;
    }

    public void start()
    {
        status = MotorStatus.RUN;
    }

    public void cleanStop()
    {
        // Wait until all grain has left the auger

        status = MotorStatus.CLEAN_STOP;
    }

    public void hardStop()
    {
        // For stopping motor immediatly

        status = MotorStatus.HARD_STOP;
    }

    public void eStop()
    {
        // would really be private, only called
        // when a motor can no longer run

        status = MotorStatus.E_STOP;
        SystemControl.eStopSystem(this);
    }

    public static List<Motor> getSystemMotors()
    {
        return systemMotors;
    }
}
