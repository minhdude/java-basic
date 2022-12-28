/*
 * @group 2
 * Name: SCP model
 * Process: Deploy the model and input data
 * Date: 28-12-2022
*/

import java.util.ArrayList;

public class SCPModel {
	// constribute
	private ArrayList<Integer> ID;
	private ArrayList<City> ListCity;

	// get-set method
	public ArrayList<Integer> getID() {
		return ID;
	}

	public void setID(ArrayList<Integer> iD) {
		ID = iD;
	}

	public ArrayList<City> getListCity() {
		return ListCity;
	}

	public void setListCity(ArrayList<City> listCity) {
		ListCity = listCity;
	}

	public SCPModel() {
		this.ID = new ArrayList<Integer>();
		this.ListCity = new ArrayList<City>();
	}

	public ArrayList<ArrayList<Double>> convertDataToArrayList() {
		ArrayList<ArrayList<Double>> time_ = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < this.getID().size(); i++) {
			ArrayList<Double> tmp = new ArrayList<>();
			tmp = this.getListCity().get(i).get_TimneToGo();
			time_.add(tmp);
				
		}
		return time_;
	}

	public void addCityToModel(int id, double cost, ArrayList<Double> elements) {
		City a = new City(id, cost, elements);
		ID.add(id);
		ListCity.add(a);
	}

	public ArrayList<ArrayList<Integer>> convertDataToBinaryMatrix(float time_contraint) {
		ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>(this.getID().size());

		ArrayList<ArrayList<Double>> time_ = new ArrayList<ArrayList<Double>>(this.getID().size());
		time_ = this.convertDataToArrayList();
		for (int i = 0; i < this.getID().size(); i++) {
			ArrayList<Integer> tmp = new ArrayList<>();
			for (int j = 0; j < this.getID().size(); j++) {
				if (time_.get(i).get(j) <= time_contraint) {
					tmp.add(1);
				} else {
					tmp.add(0);
				}
			}
			a.add(tmp);


		}
		return a;
	}

	public ArrayList<Double> getCostFromData() {
		ArrayList<Double> costArr = new ArrayList<Double>();
		for (int i = 0; i < this.getID().size(); i++) {
			costArr.add(this.getListCity().get(i).get_cost());
		}
		return costArr;

	}
	public void output()
	{
		for (int i =0; i<this.getID().size(); i++)
		{
			String a="";
			a+= this.getListCity().get(i).toString();
			System.out.println(a);
		}
	}
	
}
