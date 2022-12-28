
/*
 * @group 2
 * Name: GreedySolver
 * Process: abstract class and proceed to implement the steps of the algorithm
 * Date: 28-12-2022
*/
import java.util.ArrayList;
import java.util.Collections;

public class GreedySolver {
	private String _name;
	private SCPModel _model;
	private ArrayList<City> _solnSets;
	private double _objFn;
	private long _compTime;
	private boolean _solved;
	private float time_contraint;

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public SCPModel get_model() {
		return _model;
	}

	public void set_model(SCPModel _model) {
		this._model = _model;
	}

	public ArrayList<City> get_solnSets() {
		return _solnSets;
	}

	public void set_solnSets(ArrayList<City> _solnSets) {
		this._solnSets = _solnSets;
	}

	public double get_objFn() {
		return _objFn;
	}

	public void set_objFn(double _objFn) {
		this._objFn = _objFn;
	}

	public long get_compTime() {
		return _compTime;
	}

	public void set_compTime(long _compTime) {
		this._compTime = _compTime;
	}

	public boolean is_solved() {
		return _solved;
	}

	public void set_solved(boolean _solved) {
		this._solved = _solved;
	}

	public float getTime_contraint() {
		return time_contraint;
	}

	public void setTime_contraint(float time_contraint) {
		this.time_contraint = time_contraint;
	}

	public GreedySolver() {
		_name = "GREEDY ALOGRITHM";
		_model = null;
		reset();

	}

	public void reset() {
		_solnSets = new ArrayList<City>();
		_objFn = 0d;
		_compTime = 0;
		_solved = false;
	}

	public ArrayList<Double> CaculateCostPerElement(ArrayList<ArrayList<Integer>> a) {
		// khởi tạo và tính toán mảng giá trị
		ArrayList<Double> costArr = new ArrayList<Double>();
		costArr = this.get_model().getCostFromData();

		int count = 0;
		// khởi tạo một mảng giá trị tạm
		ArrayList<Double> costTmp = new ArrayList<Double>();

		for (int i = 0; i < this._model.getID().size(); i++) {
			count = 0;
			for (int j = 0; j < this._model.getID().size(); j++) {
				if (a.get(j).get(i) == 1) {
					count++;
				}
			}
			if (count == 0)
				costTmp.add(Double.MAX_VALUE);
			else
				costTmp.add(costArr.get(i) / count);

		}

		return costTmp;
	}

	public int findMinDifferentZero(ArrayList<Double> a) {

		double min_ = Collections.min(a);

		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) == min_) {
				return i;
			}
		}
		return -1;

	}

	public ArrayList<ArrayList<Integer>> maintainceModeling(ArrayList<ArrayList<Integer>> data, int local) {
		ArrayList<ArrayList<Integer>> data_ = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> tmpZero = new ArrayList<Integer>(data.size());
		for (int i = 0; i < data.size(); i++) {
			tmpZero.add(0);
			data_.add(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			if (data_.get(i).get(local) > 0) {
				data_.set(i, tmpZero);
			}
		}
		return data_;
	}

	public int countContain(ArrayList<ArrayList<Integer>> data, int local) {
		int count = 0;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).get(local) > 0) {
				count++;
			}
		}
		return count;

	}

	public void solve() {
		reset();
		int CountCheck = this._model.getID().size();
		ArrayList<Double> costArr = new ArrayList<Double>();
		costArr = this._model.getCostFromData();
		System.out.println();
		for (int i = 0; i < costArr.size(); i++) {
			System.out.println(costArr.get(i));
		}
		System.out.println();

		ArrayList<ArrayList<Integer>> data = this._model.convertDataToBinaryMatrix(
				this.time_contraint);

		ArrayList<Double> costTmp = new ArrayList<Double>();
		long start = System.currentTimeMillis();// bắt đầu tính thời gian chạy
		// System.out.println("Running '" + get_name() + "'...");
		this._objFn = 0;
		ArrayList<City> ctl = new ArrayList<City>();
		ctl = this._model.getListCity();

		while (CountCheck>0) {
			costTmp = CaculateCostPerElement(data);
			int minJ = findMinDifferentZero(costTmp);
			City choose = new City();
			choose = _model.getListCity().get(minJ);
			_solnSets.add(choose);
			int dem = countContain(data, minJ);
			CountCheck = CountCheck- dem; 
			this._objFn += costArr.get(minJ);
			data = maintainceModeling(data, minJ);

		}
		for (int i =0; i<_solnSets.size(); i++)
		{
			System.out.println(_solnSets.get(i).toString());
		}
		_compTime = System.currentTimeMillis() - start;
		System.out.println("Cost: " + _objFn);
		System.out.println("Done." + _compTime);
	}

	public void print() {
		System.out.println("\n'" + get_name() + "' results:");
		System.out.format("'" + get_name() + "'   Time to solve: %dms\n", _compTime);
		System.out.format("'" + get_name() + "'   Objective function value: %.2f\n", _objFn);
		System.out.format("'" + get_name() + "'   Number of sets selected: %d\n", _solnSets.size());
		System.out.format("'" + get_name() + "'   Sets selected: ");
		for (City s : _solnSets) {
			System.out.print(s.get_id() + " ");
		}
		System.out.println("\n");
	}

	public void printRowMetrics() {
		System.out.format("%-25s%12d%15.4f%17.2f\n", get_name(), _compTime, _objFn);
	}
}
