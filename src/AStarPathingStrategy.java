
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {
    AStarPathingStrategy() {
    }
    public double hval(Point end, Point p) {
        return (Math.abs(end.x - p.x) + Math.abs(end.y - p.y));
    }

    public double gval(Point p, Node cur) {
        return (cur.p.x != p.x || cur.p.y - 1 != p.y) &&
                (cur.p.x != p.x || cur.p.y + 1 != p.y) &&
                (cur.p.x + 1 != p.x || cur.p.y != p.y) &&
                (cur.p.x - 1 != p.x || cur.p.y != p.y) ? cur.g + 1.4 : cur.g + 1.0;
    }

    public double fval(Node cur, Point p, Point e) {
        return this.gval(p, cur) + this.hval(e, p);
    }

    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        HashMap<Point, Node> open = new HashMap();
        HashMap<Point, Node> close = new HashMap();
        List<Node> fvals = new ArrayList();
        List<Point> points = new ArrayList();
        Node cur = new Node(start, (Node)null, 0.0, 0.0);
        fvals.add(cur);
        open.put(start, cur);

        while(!withinReach.test(cur.p, end)) {
            List<Point> neighbors = (List)((Stream)potentialNeighbors.apply(cur.p)).filter(canPassThrough).filter((pt) -> {
                return !close.containsKey(pt);
            }).collect(Collectors.toList());
            Iterator var12 = neighbors.iterator();

            while(var12.hasNext()) {
                Point p = (Point)var12.next();
                if (!close.containsKey(p)) {
                    Node neighbor = new Node(p, cur, this.fval(cur, p, end), this.gval(p, cur));
                    double gval = 0.0;
                    if (close.get(p) != null) {
                        gval = this.gval(p, (Node)close.get(p));
                    }

                    if (open.containsKey(p) && gval < cur.g) {
                        neighbor.g = gval;
                        neighbor.f = this.hval(end, p) + gval;
                        close.replace(p, neighbor);
                    }

                    if (!open.containsKey(p)) {
                        open.put(p, neighbor);
                        fvals.add(neighbor);
                    }
                }
            }

            close.put(cur.p, cur);
            fComparator comp = new fComparator();
            Collections.sort(fvals, comp);
            if (fvals.size() == 0) {
                return points;
            }

            cur = fvals.get(0);
            fvals.remove(0);
            open.remove(cur.p);
        }

        while(cur.p != start) {
            points.add(cur.p);
            close.remove(cur.p);
            cur = cur.parent;
        }

        Collections.reverse(points);
        return points;
    }


}
