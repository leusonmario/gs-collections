/*
 * Copyright 2014 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.api.collection;

import java.util.Collection;

import com.gs.collections.api.RichIterable;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.function.Function3;
import com.gs.collections.api.block.function.primitive.BooleanFunction;
import com.gs.collections.api.block.function.primitive.ByteFunction;
import com.gs.collections.api.block.function.primitive.CharFunction;
import com.gs.collections.api.block.function.primitive.DoubleFunction;
import com.gs.collections.api.block.function.primitive.FloatFunction;
import com.gs.collections.api.block.function.primitive.IntFunction;
import com.gs.collections.api.block.function.primitive.LongFunction;
import com.gs.collections.api.block.function.primitive.ShortFunction;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.block.procedure.Procedure2;
import com.gs.collections.api.collection.primitive.MutableBooleanCollection;
import com.gs.collections.api.collection.primitive.MutableByteCollection;
import com.gs.collections.api.collection.primitive.MutableCharCollection;
import com.gs.collections.api.collection.primitive.MutableDoubleCollection;
import com.gs.collections.api.collection.primitive.MutableFloatCollection;
import com.gs.collections.api.collection.primitive.MutableIntCollection;
import com.gs.collections.api.collection.primitive.MutableLongCollection;
import com.gs.collections.api.collection.primitive.MutableShortCollection;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.MutableMultimap;
import com.gs.collections.api.partition.PartitionMutableCollection;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.api.tuple.Twin;

/**
 * MutableCollection is an interface which extends the base java.util.Collection interface and adds several internal
 * iterator methods, from the Smalltalk Collection protocol.  These include variations of forEach, select, reject,
 * detect, collect, injectInto, anySatisfy, allSatisfy. These include count, remove, partition, collectIf.  The API also
 * includes converter methods to convert a MutableCollection to a List (toList), to a sorted List (toSortedList), to a
 * Set (toSet), and to a Map (toMap).
 * <p>
 * There are several extensions to MutableCollection, including MutableList, MutableSet, and MutableBag.
 */
public interface MutableCollection<T>
        extends Collection<T>, RichIterable<T>
{
    /**
     * This method allows mutable and fixed size collections the ability to add elements to their existing elements.
     * In order to support fixed size a new instance of a collection would have to be returned taking the elements of
     * the original collection and appending the new element to form the new collection.  In the case of mutable
     * collections, the original collection is modified, and is returned.  In order to use this method properly with
     * mutable and fixed size collections the following approach must be taken:
     * <p>
     * <pre>
     * MutableCollection<String> list;
     * list = list.with("1");
     * list = list.with("2");
     * return list;
     * </pre>
     * In the case of {@link FixedSizeCollection} a new instance of MutableCollection will be returned by with, and any
     * variables that previously referenced the original collection will need to be redirected to reference the
     * new instance.  For other MutableCollection types you will replace the reference to collection with the same
     * collection, since the instance will return "this" after calling add on itself.
     *
     * @see #add(Object)
     */
    MutableCollection<T> with(T element);

    /**
     * This method allows mutable and fixed size collections the ability to remove elements from their existing elements.
     * In order to support fixed size a new instance of a collection would have to be returned contaning the elements
     * that would be left from the original collection after calling remove.  In the case of mutable collections, the
     * original collection is modified, and is returned.  In order to use this method properly with mutable and fixed
     * size collections the following approach must be taken:
     * <p>
     * <pre>
     * MutableCollection<String> list;
     * list = list.without("1");
     * list = list.without("2");
     * return list;
     * </pre>
     * In the case of {@link FixedSizeCollection} a new instance of MutableCollection will be returned by without, and
     * any variables that previously referenced the original collection will need to be redirected to reference the
     * new instance.  For other MutableCollection types you will replace the reference to collection with the same
     * collection, since the instance will return "this" after calling remove on itself.
     *
     * @see #remove(Object)
     */
    MutableCollection<T> without(T element);

    /**
     * This method allows mutable and fixed size collections the ability to add multiple elements to their existing
     * elements. In order to support fixed size a new instance of a collection would have to be returned taking the
     * elements of  the original collection and appending the new elements to form the new collection.  In the case of
     * mutable collections, the original collection is modified, and is returned.  In order to use this method properly
     * with mutable and fixed size collections the following approach must be taken:
     * <p>
     * <pre>
     * MutableCollection<String> list;
     * list = list.withAll(FastList.newListWith("1", "2"));
     * return list;
     * </pre>
     * In the case of {@link FixedSizeCollection} a new instance of MutableCollection will be returned by withAll, and
     * any variables that previously referenced the original collection will need to be redirected to reference the
     * new instance.  For other MutableCollection types you will replace the reference to collection with the same
     * collection, since the instance will return "this" after calling addAll on itself.
     *
     * @see #addAll(Collection)
     */
    MutableCollection<T> withAll(Iterable<? extends T> elements);

    /**
     * This method allows mutable and fixed size collections the ability to remove multiple elements from their existing
     * elements.  In order to support fixed size a new instance of a collection would have to be returned contaning the
     * elements that would be left from the original collection after calling removeAll.  In the case of mutable
     * collections, the original collection is modified, and is returned.  In order to use this method properly with
     * mutable and fixed size collections the following approach must be taken:
     * <p>
     * <pre>
     * MutableCollection<String> list;
     * list = list.withoutAll(FastList.newListWith("1", "2"));
     * return list;
     * </pre>
     * In the case of {@link FixedSizeCollection} a new instance of MutableCollection will be returned by withoutAll,
     * and any variables that previously referenced the original collection will need to be redirected to reference the
     * new instance.  For other MutableCollection types you will replace the reference to collection with the same
     * collection, since the instance will return "this" after calling removeAll on itself.
     *
     * @see #removeAll(Collection)
     */
    MutableCollection<T> withoutAll(Iterable<? extends T> elements);

    /**
     * Creates a new empty mutable version of the same collection type.  For example, if this instance is a FastList,
     * this method will return a new empty FastList.  If the class of this instance is immutable or fixed size (i.e.
     * SingletonList) then a mutable alternative to the class will be provided.
     */
    MutableCollection<T> newEmpty();

    MutableCollection<T> tap(Procedure<? super T> procedure);

    /**
     * Returns a MutableCollection with all elements that evaluate to true for the specified predicate.
     * <p>
     * <pre>e.g.
     * return people.<b>select</b>(new Predicate&lt;Person&gt;()
     * {
     *     public boolean value(Person person)
     *     {
     *         return person.getAddress().getCity().equals("Metuchen");
     *     }
     * });
     * </pre>
     */
    MutableCollection<T> select(Predicate<? super T> predicate);

    /**
     * Returns a MutableCollection with all elements that evaluate to true for the specified predicate2 and parameter.
     * <p>
     * <pre>e.g.
     * return integers.<b>selectWith</b>(PredicatesLite.equal(), Integer.valueOf(5));
     * </pre>
     */
    <P> MutableCollection<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    /**
     * Returns a MutableCollection with all elements that evaluate to false for the specified predicate.
     * <p>
     * <pre>e.g.
     * return people.reject(new Predicate&lt;Person&gt;()
     * {
     *     public boolean value(Person person)
     *     {
     *         return person.person.getLastName().equals("Smith");
     *     }
     * });
     * </pre>
     * <p>
     * <pre>e.g.
     * return people.reject(Predicates.attributeEqual("lastName", "Smith"));
     * </pre>
     */
    MutableCollection<T> reject(Predicate<? super T> predicate);

    /**
     * Returns a MutableCollection with all elements that evaluate to false for the specified predicate2 and parameter.
     * <p>
     * <pre>e.g.
     * return integers.<b>rejectWith</b>(PredicatesLite.equal(), Integer.valueOf(5));
     * </pre>
     */
    <P> MutableCollection<T> rejectWith(
            Predicate2<? super T, ? super P> predicate,
            P parameter);

    /**
     * Filters a collection into two separate collections based on a predicate returned via a Pair.
     * <p>
     * <pre>e.g.
     * return lastNames.<b>selectAndRejectWith</b>(PredicatesLite.lessThan(), "Mason");
     * </pre>
     */
    <P> Twin<MutableList<T>> selectAndRejectWith(
            Predicate2<? super T, ? super P> predicate,
            P parameter);

    PartitionMutableCollection<T> partition(Predicate<? super T> predicate);

    <P> PartitionMutableCollection<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter);

    <S> MutableCollection<S> selectInstancesOf(Class<S> clazz);

    /**
     * Removes all elements in the collection that evaluate to true for the specified predicate.
     * <p>
     * <pre>e.g.
     * return lastNames.<b>removeIf</b>(Predicates.isNull());
     * </pre>
     */
    void removeIf(Predicate<? super T> predicate);

    /**
     * Removes all elements in the collection that evaluate to true for the specified predicate2 and parameter.
     * <p>
     * <pre>e.g.
     * return lastNames.<b>removeIfWith</b>(PredicatesLite.isNull(), null);
     * </pre>
     */
    <P> void removeIfWith(Predicate2<? super T, ? super P> predicate, P parameter);

    /**
     * Returns a new MutableCollection with the results of applying the specified function to each element of the source
     * collection.
     * <p>
     * <pre>e.g.
     * return people.collect(new Function&lt;Person, String&gt;()
     * {
     *     public String value(Person person)
     *     {
     *         return person.getFirstName() + " " + person.getLastName();
     *     }
     * });
     * </pre>
     */
    <V> MutableCollection<V> collect(Function<? super T, ? extends V> function);

    MutableBooleanCollection collectBoolean(BooleanFunction<? super T> booleanFunction);

    MutableByteCollection collectByte(ByteFunction<? super T> byteFunction);

    MutableCharCollection collectChar(CharFunction<? super T> charFunction);

    MutableDoubleCollection collectDouble(DoubleFunction<? super T> doubleFunction);

    MutableFloatCollection collectFloat(FloatFunction<? super T> floatFunction);

    MutableIntCollection collectInt(IntFunction<? super T> intFunction);

    MutableLongCollection collectLong(LongFunction<? super T> longFunction);

    MutableShortCollection collectShort(ShortFunction<? super T> shortFunction);

    <P, V> MutableCollection<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    /**
     * Returns a new MutableCollection with the results of applying the specified function to each element of the source
     * collection, but only for elements that evaluate to true for the specified predicate.
     * <p>
     * <pre>e.g.
     * Lists.mutable.of().with(1, 2, 3).collectIf(Predicates.notNull(), Functions.getToString())
     * </pre>
     */
    <V> MutableCollection<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    <V> MutableCollection<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    <IV, P> IV injectIntoWith(
            IV injectValue,
            Function3<? super IV, ? super T, ? super P, ? extends IV> function,
            P parameter);

    /**
     * Returns an unmodifiable view of this collection.  This method allows modules to provide users with "read-only"
     * access to internal collections.  Query operations on the returned collection "read through" to this collection,
     * and attempts to modify the returned collection, whether direct or via its iterator, result in an
     * <tt>UnsupportedOperationException</tt>.
     * <p>
     * The returned collection does <i>not</i> pass the hashCode and equals operations through to the backing
     * collection, but relies on <tt>Object</tt>'s <tt>equals</tt> and <tt>hashCode</tt> methods.  This is necessary to
     * preserve the contracts of these operations in the case that the backing collection is a set or a list.<p>
     * <p>
     * The returned collection will be serializable if this collection is serializable.
     *
     * @return an unmodifiable view of this collection.
     * @since 1.0
     */
    MutableCollection<T> asUnmodifiable();

    /**
     * Returns a synchronized (thread-safe) collection backed by this collection.  In order to guarantee serial access,
     * it is critical that <strong>all</strong> access to the backing collection is accomplished through the returned
     * collection.
     * <p>
     * It is imperative that the user manually synchronize on the returned collection when iterating over it using the
     * standard JDK iterator or JDK 5 for loop.
     * <pre>
     *  MutableCollection collection = myCollection.asSynchronized();
     *     ...
     *  synchronized(collection)
     *  {
     *      Iterator i = c.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *         foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     * <p>
     * The preferred way of iterating over a synchronized collection is to use the collection.forEach() method which is
     * properly synchronized internally.
     * <pre>
     *  MutableCollection collection = myCollection.asSynchronized();
     *     ...
     *  collection.forEach(new Procedure()
     *  {
     *      public void value(Object each)
     *      {
     *          ...
     *      }
     *  });
     * </pre>
     * <p>
     * The returned collection does <i>not</i> pass the <tt>hashCode</tt> and <tt>equals</tt> operations through to the
     * backing collection, but relies on <tt>Object</tt>'s equals and hashCode methods.  This is necessary to preserve
     * the contracts of these operations in the case that the backing collection is a set or a list.
     * <p>
     * The returned collection will be serializable if this collection is serializable.
     *
     * @return a synchronized view of this collection.
     * @since 1.0
     */
    MutableCollection<T> asSynchronized();

    /**
     * Converts this MutableCollection to an ImmutableCollection.
     *
     * @since 1.0
     */
    ImmutableCollection<T> toImmutable();

    <V> MutableMultimap<V, T> groupBy(Function<? super T, ? extends V> function);

    <V> MutableMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function);

    <V> MutableMap<V, T> groupByUniqueKey(Function<? super T, ? extends V> function);

    <S> MutableCollection<Pair<T, S>> zip(Iterable<S> that);

    MutableCollection<Pair<T, Integer>> zipWithIndex();

    /**
     * @see #addAll(Collection)
     * @since 1.0
     */
    boolean addAllIterable(Iterable<? extends T> iterable);

    /**
     * @see #removeAll(Collection)
     * @since 1.0
     */
    boolean removeAllIterable(Iterable<?> iterable);

    /**
     * @see #retainAll(Collection)
     * @since 1.0
     */
    boolean retainAllIterable(Iterable<?> iterable);

    <K, V> MutableMap<K, V> aggregateInPlaceBy(
            Function<? super T, ? extends K> groupBy,
            Function0<? extends V> zeroValueFactory,
            Procedure2<? super V, ? super T> mutatingAggregator);

    <K, V> MutableMap<K, V> aggregateBy(
            Function<? super T, ? extends K> groupBy,
            Function0<? extends V> zeroValueFactory,
            Function2<? super V, ? super T, ? extends V> nonMutatingAggregator);
}
