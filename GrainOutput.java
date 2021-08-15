import java.util.LinkedList;
import java.util.List;

public class GrainOutput
{
    private String name;
    private Motor motorInput;
    private static List<GrainOutput> systemOutputs = new LinkedList<GrainOutput>();

    public GrainOutput(String _name, Motor in)
    {
        name = _name;
        motorInput = in;
        systemOutputs.add(this);
    }

    public String getName()
    {
        return name;
    }

    public Motor getMotorInput()
    {
        return motorInput;
    }

    public static List<GrainOutput> getSystemGrainOutputs()
    {
        return systemOutputs;
    }
}