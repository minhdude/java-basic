/*
 * @group 2
 * Name: Element class
 * Process: subclass initialization and subclass functions
 * Date: 28-12-2022
 */

package set_cover_problem;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

// Lớp này giữ một ID đã đặt, chi phí của nó và ID phần tử mà tập hợp bao gồm
public class ElementSet implements Comparable {

    private final int _id; // Khởi tạo ID
    private final double _cost; // Giá trị của ID
    // SortedSet: lưu trữ một danh sách các phần tử không có sự trùng lặp
    private final SortedSet<Integer> _elements; // chứa các phần tử ID mà bộ này bao phủ

    // Khởi tạo ElementSet
    // Collection: cung cấp một kiến trúc để lưu trữ và thao tác tới nhóm các đối tượng
    // TreeSet: kế thừa từ SortedSet, các phần tử trong một TreeSet được sắp xếp theo thứ tự tự nhiên của chúng.
    public ElementSet(int id, double cost, Collection<Integer> elements) {// khởi tạo
        _id = id;
        _cost = cost;
        _elements = new TreeSet<>(elements);
    }
    // get-set method
    public int getId() {
        return _id;
    }
    public double getCost() {
        return _cost;
    }

    public SortedSet<Integer> getTheseEs(){
        TreeSet<Integer> temp = new TreeSet<>();
        temp.addAll(_elements); // addAll(): dùng để thêm một tập collection vào nó như là một ArrayList hay HashSet
        return temp;
    }

    // Trả về số lượng ID phần tử trong tập hợp này bao gồm các ID phần tử trong elements_to_cover.
    public int countElementsCovered(Set<Integer> elements_to_cover) {// đếm số lượng phần tử đã được bao phủ ở trong tập con
        int count = 0;
        if(_elements.size() < elements_to_cover.size()){
            for(int i : _elements){
                if(elements_to_cover.contains(i)) // contains(): kiểm tra sự tồn tại của chuỗi trong chuỗi giúp đưa ra quyết định
                    count++;
            }
        }
        else {
            for(int i : elements_to_cover){
                if(_elements.contains(i))
                    count++;
            }
        }
        return count;
    }

    @Override
    public int compareTo(Object o) { // compareTo(): được sử dụng để thực hiện sắp xếp tự nhiên trên chuỗi
        if(!(o instanceof ElementSet e)) // instanseof: kiểm tra xem đối tượng này có phải là instance của một class hay interface nào đó hay không
            return 1;
        // ép kiểu cho đối tượng O
        if(this._id != e._id)
            return (this._id - e._id);
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        // equals(): so sánh hai chuỗi đưa ra dựa trên nội dung của chuỗi.
        // Nếu hai chuỗi khác nhau nó trả về false.
        // Nếu hai chuỗi bằng nhau nó trả về true.
        if(!(o instanceof ElementSet e))
            return false;
        return _cost == e._cost && _id == e._id;
    }

    @Override
    public String toString() {
        return String.format("Set ID: %3d   Cost: %6.2f   Element IDs: %s", _id, _cost, _elements);
    }
}
