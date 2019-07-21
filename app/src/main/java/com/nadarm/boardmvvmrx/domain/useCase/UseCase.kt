package com.nadarm.boardmvvmrx.domain.useCase

import io.reactivex.Flowable
import io.reactivex.Single

interface FlowableUseCase<Params, Type> {
    fun execute(params: Params): Flowable<Type>
}

interface SingleUseCase<Params, Type> {
    fun execute(params: Params): Single<Type>
}