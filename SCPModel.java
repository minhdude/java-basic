/*
 * @group 2
 * Name: SCP model
 * Process: Deploy the model and input data
 * Date: 28-12-2022
 */
package set_cover_problem;

import java.util.*;

// Giữ tất cả dữ liệu mô hình Set Cover Problem (SCP)
// Một SCP chứa một tập hợp các ElementSets
// Một SCP cũng lưu trữ trực tiếp tất cả các ID Phần tử duy nhất trong vấn đề để truy cập thuận tiện.
public class SCPModel {

    private SortedSet<ElementSet> _elementSets; // Một tập hợp các ElementSets, được sắp xếp theo ID
    private SortedSet<Integer> _elements; // Một tập hợp tất cả các ID phần tử trong mô hình SCP này, được sắp xếp theo
    // ID

    // hàm khởi tạo
    public SCPModel() {
        _elementSets = new TreeSet<>();
        _elements = new TreeSet<>();
    }

    // Thêm một ElementSet vào SCPModel
    public void addElementSet(int id, double cost, Collection<Integer> elements) {
        // TODO: tạo một ElementSet mới và cập nhật hai thành viên dữ liệu của lớp này
        ElementSet ES = new ElementSet(id, cost, elements);
        _elementSets.add(ES); // thêm tập con vào mô hình đang vận hành
        _elements.addAll(elements); // thêm id của tập con vào mô hình đang vận hành
    }

    // Nhận tổng số ID phần tử trong SCPModel
    public int getNumElements() {
        return _elements.size();
    }

    // Lấy tổng số Set ID trong SCPModel
    public int getNumElementSets() {
        return _elementSets.size();
    }

    //
    public SortedSet<Integer> getAllEs() {
        TreeSet<Integer> temp = new TreeSet<>();
        temp.addAll(_elements);
        return temp;
    }

    //
    public Iterable<ElementSet> getElementSetIterable() {
        return _elementSets;
    }

    // In ra chi tiết mô hình
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nWeighted SCP model:\n");
        sb.append("---------------------\n");
        sb.append("Number of elements (n): ").append(getNumElements()).append("\n");
        sb.append("Number of sets (m): ").append(getNumElementSets()).append("\n");
        sb.append("\nSet details:\n");
        String.format("%3s%10s%23s%19s\n", "No.", "Cost", "No. elements covered", "Elements covered");
        sb.append("----------------------------------------------------------\n");
        for (ElementSet e : getElementSetIterable())
            sb.append(e).append("\n");
        return sb.toString();
    }
    public boolean check() {
    	if(this._elements == null || this._elementSets == null || this.getNumElements()==0)
    		return false;
		return true;
    }
    public void set(SCPModel model) {
    	this._elements = model._elements;
    	this._elementSets = model._elementSets;
    }
}

