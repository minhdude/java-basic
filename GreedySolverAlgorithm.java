/*
 * @group 2
 * Name: GreedySolver
 * Process: Inheriting the GreedySolver class
 * Date: 28-12-2022
 */
package set_cover_problem;
public class GreedySolverAlgorithm extends GreedySolver {

    public GreedySolverAlgorithm() {
        this._name = "Greedy's algorithm";
        this.reset();
    }

    @Override
    public ElementSet nextBestSet() {
        double minratio = Double.MAX_VALUE; // khởi tạo một giá trị tạm cho tỉ lệ phủ
        double ratio; // tỉ lệ
        ElementSet Best = null;
        for (ElementSet e : _model.getElementSetIterable()) {
            ratio = e.getCost() / e.countElementsCovered(_elementsNotCovered);
            // tính tỉ lệ bao phủ của từng tập con trong dữ liệu đầu vào

            if (ratio < minratio) {
                minratio = ratio;
                Best = e;
            }
        }
        return Best;
    }
}
