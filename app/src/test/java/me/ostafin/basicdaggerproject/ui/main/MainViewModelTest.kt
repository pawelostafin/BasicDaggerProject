package me.ostafin.basicdaggerproject.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler
import me.ostafin.basicdaggerproject.domain.model.Character
import me.ostafin.basicdaggerproject.domain.model.CharactersPageResponse
import me.ostafin.basicdaggerproject.domain.model.Info
import me.ostafin.basicdaggerproject.domain.repository.EloRepository
import org.junit.*

class MainViewModelTest {

    lateinit var SUT: MainViewModel
    lateinit var eloRepositoryMock: EloRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        useTrampolineScheduler()

        val extraInt = 898L
        eloRepositoryMock = mockk()
        SUT = MainViewModel(extraInt, eloRepositoryMock)

        mockedGetCallbackResponse()
        mockedGetHeheResponse()
    }

    @After
    fun resetSchedulers() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun `repository getHehe method invoked when initializeIfNeeded is called`() {
        SUT.initializeIfNeeded()

        verify(exactly = 1) { eloRepositoryMock.getHeHe(any()) }
    }

    @Test
    fun `no interaction with repository before onInitialized is called`() {
        mockedGetHeheResponse()

        verify { eloRepositoryMock wasNot Called }
    }

    @Test
    fun `results string representation emitted in results live data when request is successful`() {
        val mockedObserver = mockkedLiveDataObserver<String>()
        SUT.resultsString.observeForever(mockedObserver)

        SUT.initializeIfNeeded()

        val values = mutableListOf<String>()
        verify(exactly = 1) { mockedObserver.onChanged(capture(values)) }
        Assert.assertEquals(RESULTS_STRING_REPRESENTATION, values.first())
    }

    @Test
    fun `results string representation emitted in results live data when request is successful 2`() {
        val mockedObserver = mockkedLiveDataObserver<String>()
        SUT.resultsString.observeForever(mockedObserver)

        SUT.initializeIfNeeded()

        verify { mockedObserver.onChanged(RESULTS_STRING_REPRESENTATION) }
    }

    @Test
    fun `true false true emmited in booleanRelay when onInitialized is called`() {
        val testObserver = SUT.booleanObs.test()

        SUT.initializeIfNeeded()

        testObserver.assertValues(true, false, true)
    }

    @Test
    fun `callback result emission on successful callback return`() {
        val testObserver = SUT.callbackResultObs.test()

        SUT.initializeIfNeeded()

        testObserver.assertValues(CALLBACK_RESULT)
    }

//    @Test
//    fun `false emmited as last value in booleanRelay 3 seconds after onInitialized is called`() {
//        mockedGetHeheResponse()
//        val testScheduler = TestScheduler()
//        useTestScheduler(testScheduler)
//
//        val testObserver = SUT.booleanObs.test()
//
//        SUT.initializeIfNeeded()
//
//        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
//        testObserver.assertValues(true, false, true)
//        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)
//        testObserver.assertValues(true, false, true, false)
//    }

    private fun mockedGetHeheResponse() {
        every { eloRepositoryMock.getHeHe(any()) } answers { Single.just(MOCKED_RESPONSE) }
    }

    private fun mockedGetCallbackResponse() {
        val lambda = slot<(Long) -> Unit>()
        every { eloRepositoryMock.heheCallback(capture(lambda)) } answers { lambda.invoke(CALLBACK_RESULT) }
    }

    private fun useTrampolineScheduler() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    private fun useTestScheduler(testScheduler: TestScheduler) {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
        RxJavaPlugins.setSingleSchedulerHandler { testScheduler }

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }
    }

    companion object {
        val CALLBACK_RESULT = 666L

        val MOCKED_RESPONSE = CharactersPageResponse(
            info = Info(
                count = 1,
                pages = 2,
                next = null,
                prev = null
            ),
            results = listOf(
                Character(
                    id = 111,
                    name = "jeje",
                    status = "alive"
                )
            )
        )

        val RESULTS_STRING_REPRESENTATION = MOCKED_RESPONSE.results.toString()

    }

    inline fun <reified T : Any> mockkedLiveDataObserver(): Observer<T> {
        val mockedObserver = mockk<Observer<T>>()
        every { mockedObserver.onChanged(any()) } returns Unit
        return mockedObserver
    }

}