/*
 * @group 2
 * Name: GreedySolver
 * Process: abstract class and proceed to implement the steps of the algorithm
 * Date: 28-12-2022
 */
package set_cover_problem;

import java.util.SortedSet;
import java.util.TreeSet;

// Lớp chính trừu tượng hóa một bộ giải SCP tham lam
public abstract class GreedySolver {
    protected String _name;			  // tên loại thuật toán
    protected double _alpha;          // mức độ bao phủ yêu cầu tối thiểu
    protected SCPModel _model;        // mô hình SCP hiện đang vận hành
    protected TreeSet<ElementSet> _solnSets; // Mảng kết quả
    SortedSet<Integer> _elementsNotCovered; // tập hợp các phần tử hiện không được bao phủ bởi giải pháp
    protected double _objFn;          // giá trị hàm mục tiêu (*tổng chi phí* của tất cả các bộ được sử dụng)
    protected double _coverage;       // phần trăm bảo hiểm thực tế đạt được
    protected long _compTime;         // thời gian tính toán (ms)
    protected boolean _solved;        // mô hình đã được giải quyết hay chưa

    // hàm khởi tạo
    public GreedySolver() {
        _name = "NAME NOT SET"; // Các lớp con nên đặt cái này trong hàm tạo của chúng
        _model = null;
        reset();
    }

    // Basic setters
    public void setMinCoverage(double alpha) {
        _alpha = alpha;
    }
    public void setModel(SCPModel model) {
        _model = model;
    }

    public String getName() {
        return _name;
    }

    // Đặt lại tất cả các giá trị (ngoại trừ SCPModel)
    public void reset() {
        _solnSets = new TreeSet<>();
        _elementsNotCovered = null;
        _objFn = 0d;
        _coverage = 0d;
        _compTime = 0;
        _solved = false;
    }

    // Chạy heuristic tham lam đơn giản -- thêm tập hợp tốt nhất tiếp theo cho đến khi một trong hai
    // (1) Đã đạt mức bao phủ, hoặc
    // (2) Không có bộ nào có thể tăng độ phủ.

    public void solve() {
        // // Đặt lại solver
        reset();

        // Khởi tạo sơ bộ
        int num_to_cover = (int)Math.ceil(_alpha * _model.getNumElements());// số lượng phần tử đã được bao phủ được tính bởi công thức
        int num_can_leave_uncovered = _model.getNumElements() - num_to_cover;// số lượng phần tử còn lại chưa được bao phủ
        boolean all_selected = false; // check: Tất cả các bộ đã được chọn chưa?

        //TODO: Khởi tạo phần sau thành bộ ID phần tử ban đầu chính xác
        //       không được bao gồm (nghĩa là phải là tất cả ID phần tử trong SCPModel).
        _elementsNotCovered = new TreeSet<>(); // khởi tạo một cấu trúc dữ liệu lưu trữ tập hợp chưa được bao phủ
        _elementsNotCovered.addAll(_model.getAllEs());// gán dữ liệu của mô hình vào cấu trúc dữ liệu đã khai báo



        long start = System.currentTimeMillis();// bắt đầu tính thời gian chạy
        System.out.println("Running '" + getName() + "'...");
        // chạy vào while với điều kiện:
        /*
         * 1) số lượng phần tử chưa được bao phủ lớn hơn 0
         * 2) số lượng phần tử chưa được bao phủ lớn hơn số lượng phần tử còn lại chưa được bao phủ
         * 3) các phần tử chưa được duyệt hết.
         */

        // Bắt đầu vòng lựa chọn tham lam
        while (_elementsNotCovered.size() > 0 && _elementsNotCovered.size() > num_can_leave_uncovered && !all_selected) {

            //TODO: Lấy ElementSet tốt nhất tiếp theo để thêm vào (nếu có).
            ElementSet CurrBestSet = nextBestSet(); // khởi tạo tập con có giá trị/1 phần tử có giá trị nhỏ nhất
            if(CurrBestSet == null) // nếu không tìm thấy thì ta thoát khỏi vòng while
                break;
            _elementsNotCovered.removeAll(CurrBestSet.getTheseEs()); // danh sách tập con chưa được bao phủ thực thi xóa bỏ tập con đã được chọn
            _solnSets.add(CurrBestSet);// danh sách tập con kết quả thêm tập con đã được chọn
            _objFn += CurrBestSet.getCost(); // tính lại giá trị
            System.out.println("- Selected: " + CurrBestSet); // in ra màn hình tập con đã được chọn


            //TODO: Cập nhật solnSets, objFn, elements_not_covered, và all_selected.
            if(_solnSets.size() == _model.getNumElementSets())
                all_selected = true;

        }

        // Đặt lại coverage, solved, compTime và in cảnh báo nếu có
        _coverage = (_model.getNumElements() - _elementsNotCovered.size())/(double)_model.getNumElements();
        _solved = true;
        _compTime = System.currentTimeMillis() - start;
        if (_coverage < _alpha)
            System.out.format("\nWARNING: Impossible to reach %.2f%% coverage level.\n", 100*_alpha);

        System.out.println("Done.");
    }

    // Sẽ được thực hiện trong các lớp con.
    // LƯU Ý: nếu không có bộ nào có thể cải thiện giải pháp, trả về null để cho phép thuật toán tham lam chấm dứt.
    public abstract ElementSet nextBestSet();

    // In giải pháp
    public void print() {
        System.out.println("\n'" + getName() + "' results:");
        System.out.format("'" + getName() + "'   Time to solve: %dms\n", _compTime);
        System.out.format("'" + getName() + "'   Objective function value: %.2f\n", _objFn);
        System.out.format("'" + getName() + "'   Coverage level: %.2f%% (%.2f%% minimum)\n", 100*_coverage, 100*_alpha);
        System.out.format("'" + getName() + "'   Number of sets selected: %d\n", _solnSets.size());
        System.out.format("'" + getName() + "'   Sets selected: ");
        for (ElementSet s : _solnSets) {
            System.out.print(s.getId() + " ");
        }
        System.out.println("\n");
    }

}
