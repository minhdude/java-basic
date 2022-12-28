
/*
 * @group 2
 * Name: Element class
 * Process: subclass initialization and subclass functions
 * Date: 28-12-2022
*/
import java.util.ArrayList;


public class City {

    private int _id; // Khởi tạo ID
    private double _cost; // Giá trị của ID
    private ArrayList<Double> _timeToGo; // chứa các phần tử ID mà bộ này bao phủ



    public City() {

    }

    public City(int id, double cost, ArrayList<Double> timeTG) {// khởi tạo
        _id = id;
        _cost = cost;
        _timeToGo = new ArrayList<Double>();
        _timeToGo = timeTG;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public double get_cost() {
        return _cost;
    }

    public void set_cost(double _cost) {
        this._cost = _cost;
    }

    public ArrayList<Double> get_TimneToGo() {
        return _timeToGo;
    }

    public void set_TimeToGo(ArrayList<Double> elements) {
        this._timeToGo = elements;
    }


    @Override
    public String toString() {
        return String.format("Set ID: %3d   Cost: %6.2f   Element IDs: %s", _id, _cost, _timeToGo);
    }
}
