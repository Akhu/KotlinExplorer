# KotlinExplorer

Playground and sample for mastering Kotlin 

## Concurrent
![](https://i.imgur.com/af16iwf.png)

Coroutines are concurrent.
And can run in parallel.

## Coroutine
Think of Coroutines as lightweight threads that are used to perform tasks asynchronously, a.k.a. non-blocking programming.

Coroutine are so lightweight that you can easily create a thousand of them without any performance concern.

To debug coroutines : `-Dkotlinx.coroutines.debug`

## Launch vs Async

They are essentially the same. `launch` is a "fire and forget" kind of task, `async` will return some result. 

You should always keep a reference to an `async` coroutine

| launch                              | async                                                                  |
| ----------------------------------- | ---------------------------------------------------------------------- |
| Generate a Job()                    | Generate a Deferred (which is a kind of job containing a future value) |
| No return of data, only side-effect | Can return data in future                                              |
| `non-blocking`                                   | `non-blocking`                                                                       |

## Blocking vs Non Blocking

Blocking means that a function call will pause the execution of the programme until that function is done.

When you are in a non blocking context, the next line of code will be executed even if a function has not returned it value yet.

Kotlin helper function to make blocking call of coroutines (wait that coroutine finish before executing next line of code)
- `runBlocking` 
- `measureTimeMillis`
- `withTimeoutOrNull`
- ...

## Suspend

- `blocking`
- `concurrent`
- A suspending function is a regular function that suspend the execution of the calling coroutine.

## Job

A Job is a reference to a coroutine that can have children and a parent.

![](https://i.imgur.com/C4Et7XZ.png)

- A Job gives you a control over a coroutine.
- Job have a lifecycle

![](https://i.imgur.com/H8yNRYR.png)


You can use `SupervisorJob` to keep control over children coroutines executions.

:::spoiler Behaviors of Jobs
- Cancellation of parent with cancel or its exceptional completion (failure) immediately cancels all its children.
- Parent cannot complete until all its children are complete. Parent waits for all its children to complete in completing or cancelling state.
- Uncaught exception in a child, by default, cancels parent. In particular, this applies to children created with launch coroutine builder. Note, that async and other future-like coroutine builders do not have uncaught exceptions by definition, since all their exceptions are caught and are encapsulated in their result.
:::

## Dispatchers

A dispatcher is a pool of threads that have a specific role / task and is optimized for it by the language.

- `Dispatchers.IO`
- `Dispatchers.Unconfined`
- `Dispatchers.Main`
- `Dispatchers.Default`

## Coroutine Scope

A coroutine Scope is what tie everything together with control.

![](https://i.imgur.com/UX4QRiM.png)

It's recommended by Kotlin to create your own coroutine scope and not use the `GlobalScope` which handle many jobs and can conflict with your work (or you can conflict with its job)

```kotlin=
//My supervisor job to have it as parent of
//every jobs that are created from this coroutine scope
val mySupervisorJob = SupervisorJob()

//Exception handler on which all coroutines exceptions
// will be traced back
val handler = CoroutineExceptionHandler { context, exception ->
    println("$context with exception=${exception.message}")
}

//Finally my super CoroutineScope
val coroutineScopeCustom = CoroutineScope(Dispatchers.IO 
                                + mySupervisorJob 
                                + handler)
```

## Sequence

- `blocking`
- `synchronous`
- Synchronous and sequential treatment of a list
- https://kotlinlang.org/docs/reference/sequences.html

## Channel

- `non-blocking`
- `concurrent`
- `hot`
- Hot stream of data, channel are used to treat data as continous streams. Channel live inside coroutines and can exchange data between coroutines.

## Flow

- `non-blocking`
- `concurrent`
- `cold`
- Cold stream of data. Flow are like Sequence but asynchronous. Each step of a Flow transformation is async and non-blocking.



## Backpressure

Backpressure happen when an Observable produce quicker than it(s) observers can ingest.

## Streams - Cold VS Hot



| ❄️ Cold                               | 🔥 Hot                                                                     |
| ---------------------------------- | ----------------------------------------------------------------------- |
| Emit a particular sequence of data | Emit a potentially infinite sequence of data                            |
| Sequence integrity is safe                                   | No integrity guaranteed                                                                         |
| Emit only when Observer is ready   | Emit immedialtely without waiting observers to be ready (Back pressure) |

- http://reactivex.io/documentation/operators/backpressure.html
