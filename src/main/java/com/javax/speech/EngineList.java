package javax.speech;

import java.util.Vector;


/**
 * EngineList is a container for a set of EngineModeDesc objects.
 * An EngineList is used in the selection of speech
 * engines in conjunction with the methods of the Central class.
 * It provides convenience methods for the purpose of testing and manipulating the
 * EngineModeDesc objects it contains.
 * <p>
 * An EngineList object is typically obtained through the
 * availableSynthesizers or availableRecognizers
 * methods of the javax.speech.Central class.
 * The orderByMatch, anyMatch, requireMatch and
 * rejectMatch methods are used to prune the
 * list to find the best match given multiple criteria.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EngineList extends Vector {

    /**
     * Return true if one or more EngineModeDesc
     * in the EngineList match
     * the required properties.  The require object
     * is tested with the match method of each
     * EngineModeDesc in the list.  If any match call returns
     * true then this method returns true.
     * <p>
     * anyMatch is often used to test whether
     * pruning a list (with requireMatch or
     * rejectMatch) would leave the list empty.
     *
     * @see javax.speech.EngineModeDesc#match(javax.speech.EngineModeDesc)
     */
    public synchronized boolean anyMatch(EngineModeDesc require) {
        for (int i = 0; i < this.size(); ++i) {
            EngineModeDesc engineModeDesc = (EngineModeDesc) this.elementAt(i);
            if (engineModeDesc.match(require)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Order the list so that elements matching the required
     * features are at the head of the list, and others are
     * at the end.  Within categories, the original order of
     * the list is preserved.  Example:
     * <p>
     * // Put running engines at the head of the list.
     * EngineList list = ....;
     * EngineModeDesc desc = new EngineModeDesc();
     * desc.setRunning(true);
     * list.orderByMatch(desc);
     */
    public synchronized void orderByMatch(EngineModeDesc require) {
        EngineList list1 = new EngineList();
        EngineList list2 = new EngineList();

        for (int i = 0; i < this.size(); ++i) {
            EngineModeDesc engineModeDesc = (EngineModeDesc) this.elementAt(i);
            if (engineModeDesc.match(require)) {
                list1.addElement(engineModeDesc);
            } else {
                list2.addElement(engineModeDesc);
            }
        }

        this.removeAllElements();

        for (int i = 0; i < list1.size(); ++i) {
            this.addElement(list1.elementAt(i));
        }

        for (int i = 0; i < list2.size(); ++i) {
            this.addElement(list2.elementAt(i));
        }
    }

    /**
     * Remove EngineModeDesc entries from the list
     * that do match require.  The match
     * method for each EngineModeDesc in the list is
     * called: if it returns true it is removed from
     * the list.  Example:
     * <p>
     * // Remove engine mode descriptors if they support US English.
     * EngineList list = ....;
     * EngineModeDesc desc = new EngineModeDesc(Locale.US);
     * list.rejectMatch(desc);
     *
     * @see javax.speech.EngineModeDesc#match(javax.speech.EngineModeDesc)
     */
    public synchronized void rejectMatch(EngineModeDesc reject) {
        for (int i = this.size() - 1; i >= 0; --i) {
            EngineModeDesc engineModeDesc = (EngineModeDesc) this.elementAt(i);
            if (engineModeDesc.match(reject)) {
                this.removeElementAt(i);
            }
        }
    }

    /**
     * Remove EngineModeDesc entries from the list
     * that do not match require.  The match
     * method for each EngineModeDesc in the list is
     * called: if it returns false it is removed from
     * the list.  Example:
     * <p>
     * // Remove engine mode descriptors from a list if they
     * // don't support US English.
     * EngineList list = ....;
     * EngineModeDesc desc = new EngineModeDesc(Locale.US);
     * list.requireMatch(desc);
     *
     * @see javax.speech.EngineModeDesc#match(javax.speech.EngineModeDesc)
     */
    public synchronized void requireMatch(EngineModeDesc require) {
        for (int i = this.size() - 1; i >= 0; --i) {
            EngineModeDesc engineModeDesc = (EngineModeDesc) this.elementAt(i);
            if (!engineModeDesc.match(require)) {
                this.removeElementAt(i);
            }
        }
    }
}
