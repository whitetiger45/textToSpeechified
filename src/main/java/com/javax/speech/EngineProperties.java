package javax.speech;

import java.awt.Component;
import java.beans.PropertyChangeListener;


/**
 * An EngineProperties object defines the set of run-time
 * properties of an Engine.  This interface is extended for
 * each type of speech engine.  SynthesizerProperties and
 * RecognizerProperties define the additional run-time
 * properties of synthesizers and recognizers respectively.
 * The EngineProperties object for an Engine is
 * obtained from the getEngineProperties method of the engine,
 * and should be cast appropriately for the type of engine.  For example:
 * <p>
 * Synthesizer synth = ...;
 * SynthesizerProperties props = (SynthesizerProperties)synth.getEngineProperties();
 * <p>
 * Each property of an engine has a set and get method.  The method
 * signatures follow the JavaBeans design patterns (outlined below).
 * <p>
 * The run-time properties of an engine affect the behavior of a
 * running engine.  Technically, properties affect engines in the
 * ALLOCATED state.  Normally, property changes are made
 * on an ALLOCATED engine and take effect immediately
 * or soon after the change call.
 * <p>
 * The EngineProperties object for an engine is, however,
 * available in all states of an Engine.  Changes
 * made to the properties of engine in the DEALLOCATED or
 * the ALLOCATING_RESOURCES state take effect when the
 * engine next enters the ALLOCATED state.  A typical
 * scenario for setting the properties of a non-allocated is determining
 * the initial state of the engine.  For example, setting the initial
 * voice of a Synthesizer, or the initial SpeakerProfile
 * of a Recognizer.  (Setting these properties prior to
 * allocation is desirable because allocating the engine and then changing
 * the voice or the speaker can be computationally expensive.)
 * <p>
 * When setting any engine property:
 * <p>
 * The engine may choose to ignore a set value either because
 * it does not support changes in a property or because it is out-of-range.
 * The engine will apply the property change as soon as possible, but
 * the change is not necessarily immediate.  Thus, all set methods are
 * asynchronous (call may return before the effect takes place).
 * All properties of an engine are bound properties - JavaBeans
 * terminology for a property for which an event is issued when the
 * property changes.  A PropertyChangeListener can be
 * attached to the EngineProperties object to receive a
 * property change event when a change takes effect.
 * <p>
 * For example, a call to the setPitch method of the
 * SynthesizerProperties interface to change  pitch from 120Hz to 200Hz
 * might fail because the value is out-of-range.  If it does succeed, the
 * pitch change might be deferred until the synthesizer can make the
 * change by waiting to the end of the current word, sentence, paragraph
 * or text object.  When the change does take effect, a PropertyChangeEvent
 * is issued to all attached listeners with the name of the changed
 * property ("Pitch"), the old value (120) and the new value (200).
 * <p>
 * Set calls take effect in the order in which they are received.
 * If multiple changed are requested for a single property,
 * a separate event is issued for each call, even if the multiple changes
 * take place simultaneously.
 * <p>
 * The properties of an engine are persistent across sessions where possible.
 * It is the engine's responsibility to store and restore the property
 * settings.  In multi-user and client-server environments the store/restore
 * policy is at the discretion of the engine.
 * <p>
 * Control Component
 * <p>
 * An engine may provide a control object through the getControlComponent
 * method of its EngineProperties object.
 * The control object is an AWT Component.  If provided, that
 * component can be displayed to a user for customization of the engine.
 * Because the component is implemented by the engine, the display may
 * support customization of engine-specific properties that are not
 * accessible through the standard properties interfaces.
 * <p>
 * JavaBeans Properties
 * <p>
 * The JavaBeans property patterns are followed for engine properties.
 * A property is defined by its name and its property type (for example,
 * "Pitch" is a float).  The property is accessed through get and set
 * methods.  The signature of the property accessor methods are:
 * <p>
 * public void set
 * PropertyName
 * (
 * PropertyType
 * value);
 * public
 * PropertyType
 * get
 * PropertyName
 * ();
 * <p>
 * For boolean-valued properties, the  get
 * PropertyName
 * ()
 * may also be  is
 * PropertyName
 * ()
 * <p>
 * For indexed properties (arrays) the signature of the
 * property accessor methods are:
 * <p>
 * public void set
 * PropertyName
 * (
 * PropertyType
 * []; value);
 * public void set
 * PropertyName
 * (int i,
 * PropertyType
 * value);
 * public
 * PropertyType
 * [] get
 * PropertyName
 * ();
 * public
 * PropertyType
 * get
 * PropertyName
 * (int i);
 * <p>
 * For example speaking rate (for a Synthesizer) is a floating value
 * and has the following methods:
 * <p>
 * public void setSpeakingRate(float value);
 * public float getSpeakingRate();
 *
 * @see javax.speech.Engine
 * @see javax.speech.Engine#getEngineProperties()
 * @see javax.speech.recognition.RecognizerProperties
 * @see javax.speech.synthesis.SynthesizerProperties
 * @see "/products/jdk/1.2/docs/api/index.html"
 * @see java.beans.PropertyChangeEvent
 */
public interface EngineProperties {

    /**
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties of the engine..
     * <p>
     * A PropertyChangeEvent is fired in response to setting
     * any bound property.
     *
     * @param listener - The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Obtain the AWT Component that provides the default user interface
     * for setting the properties of this Engine.
     * If this Engine has no default control panel,
     * the return is null
     */
    Component getControlComponent();

    /**
     * Remove a PropertyChangeListener from the listener list.
     *
     * @param listener - The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * The reset method returns all properties to reasonable defaults
     * for the Engine.  A property change event is issued
     * for each engine property that changes as the reset takes effect.
     */
    void reset();
}
