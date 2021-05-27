package com.elrond.erdkotlin.domain.dns

import com.elrond.erdkotlin.domain.vm.QueryContractUsecase
import com.elrond.erdkotlin.domain.wallet.models.Address
import java.math.BigInteger

class GetDnsRegistrationCostUsecase internal constructor(
    private val queryContractUsecase: QueryContractUsecase,
    private val computeDnsAddressUsecase: ComputeDnsAddressUsecase
) {

    fun execute(shardId: Byte): BigInteger {
        return execute(computeDnsAddressUsecase.execute(shardId))
    }

    fun execute(dnsAddress: Address): BigInteger {
        val result = queryContractUsecase.execute(dnsAddress, "getRegistrationCost")
        return when {
            result.returnData.isNullOrEmpty() -> 0.toBigInteger()
            else -> result.returnData[0].asBigInt
        }
    }
}
