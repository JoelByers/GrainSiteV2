import java.util.LinkedList;
import java.util.List;

public class View
{ 
    private static List<Motor> searchList = new LinkedList<Motor>();

    public static void motorInputs(Motor m)
    {
        System.out.print("Motor : " + m.getName() + "\nInputs: ");

        for(int i = 0; i < m.getInputs().size(); i++)
        {
            System.out.print(m.getInputs().get(i).getName()+ ", ");
        }

        System.out.print("\n\n");
    } 

    public static void motorOutputs(Motor m)
    {
        System.out.print("Motor : " + m.getName() + "\nOutputs: ");

        for(int i = 0; i < m.getOutputs().size(); i++)
        {
            System.out.print(m.getOutputs().get(i).getName()+ ", ");
        }

        System.out.print("\n\n");
    } 

    public static void systemStatus()
    {
        for(int i = 0; i < Motor.getSystemMotors().size(); i++)
        {
            System.out.print("[" + String.format("%-10s", Motor.getSystemMotors().get(i).getName()) + "]");
            System.out.print("[" + getStatusString(Motor.getSystemMotors().get(i).getStatus()) + "]\n");
        }

        System.out.println();
    }
    
    public static void grainPath(GrainInput grainIn, GrainOutput grainOut)
    {
        for(int i = 0; i < grainIn.getMotorOutputs().size(); i++)
        {
            searchNextMotor(grainOut.getMotorInput(), grainIn.getMotorOutputs().get(i));
        }

        System.out.print("{" + grainIn.getName() + "}=>");

        for(int i = searchList.size() - 1; i >=0 ; i--)
        {
            System.out.print("[" + searchList.get(i).getName()+ "]=>");
        }

        System.out.print("{" + grainOut.getName() + "}\n\n");

        searchList.clear();
    }

    private static boolean searchNextMotor(Motor thisMotor, Motor source)
    {
        // Go fron output to input

        searchList.add(thisMotor);

        if(thisMotor == source)
        {
            return true;
        }
        else if(thisMotor.getInputs().size() == 0)
        {
            searchList.remove(thisMotor);
        }
        else
        {
            for(int i = 0; i < thisMotor.getInputs().size(); i++)
            {
                if(searchNextMotor(thisMotor.getInputs().get(i), source))
                {
                    return true;
                }
            }

            searchList.remove(thisMotor);
        }

        return false;
    }

    private static String getStatusString(MotorStatus m)
    {
        String c = " ";

        switch(m)
        {
            case RUN: c = "R";
            break;
            case CLEAN_STOP: c = "C";
            break;
            case HARD_STOP: c = "H";
            break;
            case E_STOP: c = "E";
        }

        return c;
    }
}
