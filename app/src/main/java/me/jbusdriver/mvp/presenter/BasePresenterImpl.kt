package me.jbusdriver.mvp.presenter

import com.cfzx.mvp.view.BaseView
import io.reactivex.disposables.CompositeDisposable
import me.jbusdriver.common.KLog
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2016/11/25 0025.
 */
open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    @JvmField protected var mView: V? = null
    protected var isFirstStart: Boolean by Delegates.notNull()
    protected val rxManager by lazy { CompositeDisposable() }
    protected val TAG: String by lazy { this.javaClass.simpleName }

    init {
        KLog.t(TAG)
    }

    override fun onViewAttached(view: V) {
        KLog.t(TAG).e("$this:onViewAttached")
        mView = view
        assert(mView != null)
    }


    override fun onStart(firstStart: Boolean) {
        isFirstStart = firstStart
        KLog.t(TAG).e("onStart", firstStart)
        if (firstStart && this !is BasePresenter.LazyLoaderPresenter) {
            onFirstLoad()
        }
    }


    //可以初始化加载数据
    override fun onFirstLoad() {
        KLog.t(TAG).e("onFirstLoad")
    }

    override fun onResume() {
        KLog.t(TAG).e("onResume")
    }

    override fun onPause() {
        KLog.t(TAG).e("onPause")
    }

    override fun onStop() {
        KLog.t(TAG).e("onStop")
    }


    override fun onViewDetached() {
        KLog.t(TAG).e("onViewDetached")
        mView?.dismissLoading()
        rxManager.clear()
        mView = null

    }

    override fun onPresenterDestroyed() {
        KLog.t(TAG).e("$this:onPresenterDestroyed:" + this)
        rxManager.clear()
        rxManager.dispose()
    }

}