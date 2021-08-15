import java.util.LinkedList;
import java.util.List;

public class GrainInput
{
    private String name;
    private List<Motor> motorOutputs = new LinkedList<Motor>();
    private static List<GrainInput> systemGrainInputs = new LinkedList<GrainInput>();

    public GrainInput(String _name, Motor out)
    {
        name = _name;
        systemGrainInputs.add(this);
        motorOutputs.add(out);
    }

    public GrainInput(String _name, Motor outOne, Motor outTwo)
    {
        name = _name;
        motorOutputs.add(outOne);
        motorOutputs.add(outTwo);
    }

    public GrainInput(String _name, List<Motor> outMotors)
    {
        name = _name;

        for(int i = 0; i < outMotors.size(); i++)
        {
            motorOutputs.add(outMotors.get(i));
        }
    }

    public static List<GrainInput> getSystemGrainInputs()
    {
        return systemGrainInputs;
    }

    public String getName()
    {
        return name;
    }

    public List<Motor> getMotorOutputs()
    {
        return motorOutputs;
    }
}
