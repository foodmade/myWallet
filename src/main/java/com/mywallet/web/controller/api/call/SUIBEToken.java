package com.mywallet.web.controller.api.call;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class SUIBEToken extends Contract {
    private static final String BINARY = "60806040526000805460ff60a01b1916905534801561001d57600080fd5b50600080546001600160a01b031916331790556040805180820190915260038082527f535549000000000000000000000000000000000000000000000000000000000060209092019182526100749160029161012d565b506040805180820190915260058082527f535549424500000000000000000000000000000000000000000000000000000060209092019182526100b99160039161012d565b506004805460ff191660081790556305f5e1006005819055600080546001600160a01b0390811682526006602090815260408084208590558354815195865290519216937fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929081900390910190a36101c8565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061016e57805160ff191683800117855561019b565b8280016001018555821561019b579182015b8281111561019b578251825591602001919060010190610180565b506101a79291506101ab565b5090565b6101c591905b808211156101a757600081556001016101b1565b90565b610f0f80620001d86000396000f3fe6080604052600436106101145760003560e01c806370a08231116100a0578063cae9ca5111610064578063cae9ca51146103fb578063d7972580146104c3578063dc39d06d146104f6578063dd62ed3e1461052f578063f2fde38b1461056a57610114565b806370a08231146103165780638da5cb5b1461034957806395d89b411461037a578063a9059cbb1461038f578063bd1870a3146103c857610114565b8063313ce567116100e7578063313ce5671461025a57806342966c68146102855780635c658165146102af57806362a5af3b146102ea5780636a28f0001461030157610114565b806306fdde0314610119578063095ea7b3146101a357806318160ddd146101f057806323b872dd14610217575b600080fd5b34801561012557600080fd5b5061012e61059d565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610168578181015183820152602001610150565b50505050905090810190601f1680156101955780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101af57600080fd5b506101dc600480360360408110156101c657600080fd5b506001600160a01b03813516906020013561062b565b604080519115158252519081900360200190f35b3480156101fc57600080fd5b506102056106c8565b60408051918252519081900360200190f35b34801561022357600080fd5b506101dc6004803603606081101561023a57600080fd5b506001600160a01b0381358116916020810135909116906040013561070b565b34801561026657600080fd5b5061026f61084b565b6040805160ff9092168252519081900360200190f35b34801561029157600080fd5b506101dc600480360360208110156102a857600080fd5b5035610854565b3480156102bb57600080fd5b50610205600480360360408110156102d257600080fd5b506001600160a01b038135811691602001351661091b565b3480156102f657600080fd5b506102ff610938565b005b34801561030d57600080fd5b506102ff61098b565b34801561032257600080fd5b506102056004803603602081101561033957600080fd5b50356001600160a01b03166109d8565b34801561035557600080fd5b5061035e6109f3565b604080516001600160a01b039092168252519081900360200190f35b34801561038657600080fd5b5061012e610a02565b34801561039b57600080fd5b506101dc600480360360408110156103b257600080fd5b506001600160a01b038135169060200135610a5a565b3480156103d457600080fd5b506102ff600480360360208110156103eb57600080fd5b50356001600160a01b0316610b3f565b34801561040757600080fd5b506101dc6004803603606081101561041e57600080fd5b6001600160a01b038235169160208101359181019060608101604082013564010000000081111561044e57600080fd5b82018360208201111561046057600080fd5b8035906020019184600183028401116401000000008311171561048257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610b9f945050505050565b3480156104cf57600080fd5b506102ff600480360360208110156104e657600080fd5b50356001600160a01b0316610d1d565b34801561050257600080fd5b506101dc6004803603604081101561051957600080fd5b506001600160a01b038135169060200135610d83565b34801561053b57600080fd5b506102056004803603604081101561055257600080fd5b506001600160a01b0381358116916020013516610e25565b34801561057657600080fd5b506102ff6004803603602081101561058d57600080fd5b50356001600160a01b0316610e50565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106235780601f106105f857610100808354040283529160200191610623565b820191906000526020600020905b81548152906001019060200180831161060657829003601f168201915b505050505081565b60008054600160a01b900460ff161561064357600080fd5b3360009081526001602052604090205460ff161561066057600080fd5b3360008181526007602090815260408083206001600160a01b03881680855290835292819020869055805186815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a35060015b92915050565b600080805260066020527f54cdd369e4e8a8515e52ca72ec816c2101831ad1f18bf44102ed171459c9b4f8546005546107069163ffffffff610eb516565b905090565b60008054600160a01b900460ff161561072357600080fd5b3360009081526001602052604090205460ff161561074057600080fd5b6001600160a01b038416600090815260066020526040902054610769908363ffffffff610eb516565b6001600160a01b03851660009081526006602090815260408083209390935560078152828220338352905220546107a6908363ffffffff610eb516565b6001600160a01b0380861660009081526007602090815260408083203384528252808320949094559186168152600690915220546107ea908363ffffffff610eca16565b6001600160a01b0380851660008181526006602090815260409182902094909455805186815290519193928816927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef92918290030190a35060019392505050565b60045460ff1681565b60008054600160a01b900460ff161561086c57600080fd5b3360009081526001602052604090205460ff161561088957600080fd5b3361089357600080fd5b6005546108a6908363ffffffff610eb516565b600555336000908152600660205260409020546108c9908363ffffffff610eb516565b336000818152600660209081526040808320949094558351868152935191937fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929081900390910190a3506001919050565b600760209081526000928352604080842090915290825290205481565b6000546001600160a01b0316331461094f57600080fd5b6000805460ff60a01b1916600160a01b1781556040517f962a6139ca22015759d0878e2cf5d770dcb8152e1d5ba08e46a969dd9b154a9c9190a1565b6000546001600160a01b031633146109a257600080fd5b6000805460ff60a01b191681556040517ff0daac2271a735ea786b9adf80dfcbd6a3cbd52f3cab0a78337114692d5faf5d9190a1565b6001600160a01b031660009081526006602052604090205490565b6000546001600160a01b031681565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156106235780601f106105f857610100808354040283529160200191610623565b60008054600160a01b900460ff1615610a7257600080fd5b3360009081526001602052604090205460ff1615610a8f57600080fd5b33600090815260066020526040902054610aaf908363ffffffff610eb516565b33600090815260066020526040808220929092556001600160a01b03851681522054610ae1908363ffffffff610eca16565b6001600160a01b0384166000818152600660209081526040918290209390935580518581529051919233927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a350600192915050565b6000546001600160a01b03163314610b5657600080fd5b6001600160a01b038116600081815260016020526040808220805460ff19169055517f687691c08a3e67a160ba20a32cb1c56791955f12c5ff5d5fcf62bc456ad79ea19190a250565b60008054600160a01b900460ff1615610bb757600080fd5b3360009081526001602052604090205460ff1615610bd457600080fd5b3360008181526007602090815260408083206001600160a01b03891680855290835292819020879055805187815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a3604051638f4ffcb160e01b815233600482018181526024830186905230604484018190526080606485019081528651608486015286516001600160a01b038a1695638f4ffcb195948a94938a939192909160a490910190602085019080838360005b83811015610cac578181015183820152602001610c94565b50505050905090810190601f168015610cd95780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b158015610cfb57600080fd5b505af1158015610d0f573d6000803e3d6000fd5b506001979650505050505050565b6000546001600160a01b03163314610d3457600080fd5b6001600160a01b0381166000818152600160208190526040808320805460ff1916909217909155517f169aadf55dc2098830ccf9f334e3ce3933b6e895b9114fc9f49242f2be61fe8e9190a250565b600080546001600160a01b03163314610d9b57600080fd5b600080546040805163a9059cbb60e01b81526001600160a01b0392831660048201526024810186905290519186169263a9059cbb926044808401936020939083900390910190829087803b158015610df257600080fd5b505af1158015610e06573d6000803e3d6000fd5b505050506040513d6020811015610e1c57600080fd5b50519392505050565b6001600160a01b03918216600090815260076020908152604080832093909416825291909152205490565b6000546001600160a01b03163314610e6757600080fd5b600080546001600160a01b0319166001600160a01b0383811691821780845560405192939116917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a350565b600082821115610ec457600080fd5b50900390565b818101828110156106c257600080fdfea265627a7a72315820a4d69ff839b38b9ded3d676a8b3e08c5471e28dbbdea56762266cb039587353d64736f6c634300050c0032";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_ALLOWED = "allowed";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_APPROVEANDCALL = "approveAndCall";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_FREEZE = "freeze";

    public static final String FUNC_LOCKUSER = "lockUser";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERANYERC20TOKEN = "transferAnyERC20Token";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UNFREEZE = "unfreeze";

    public static final String FUNC_UNLOCKUSER = "unlockUser";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FREEZED_EVENT = new Event("Freezed", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event LOCKUSER_EVENT = new Event("LockUser", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNFREEZED_EVENT = new Event("UnFreezed", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event UNLOCKUSER_EVENT = new Event("UnlockUser", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected SUIBEToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SUIBEToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SUIBEToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SUIBEToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.tokenOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<FreezedEventResponse> getFreezedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(FREEZED_EVENT, transactionReceipt);
        ArrayList<FreezedEventResponse> responses = new ArrayList<FreezedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            FreezedEventResponse typedResponse = new FreezedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FreezedEventResponse> freezedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FreezedEventResponse>() {
            @Override
            public FreezedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(FREEZED_EVENT, log);
                FreezedEventResponse typedResponse = new FreezedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<FreezedEventResponse> freezedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FREEZED_EVENT));
        return freezedEventFlowable(filter);
    }

    public List<LockUserEventResponse> getLockUserEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOCKUSER_EVENT, transactionReceipt);
        ArrayList<LockUserEventResponse> responses = new ArrayList<LockUserEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LockUserEventResponse typedResponse = new LockUserEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LockUserEventResponse> lockUserEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LockUserEventResponse>() {
            @Override
            public LockUserEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOCKUSER_EVENT, log);
                LockUserEventResponse typedResponse = new LockUserEventResponse();
                typedResponse.log = log;
                typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LockUserEventResponse> lockUserEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOCKUSER_EVENT));
        return lockUserEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokens = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public List<UnFreezedEventResponse> getUnFreezedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UNFREEZED_EVENT, transactionReceipt);
        ArrayList<UnFreezedEventResponse> responses = new ArrayList<UnFreezedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UnFreezedEventResponse typedResponse = new UnFreezedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UnFreezedEventResponse> unFreezedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UnFreezedEventResponse>() {
            @Override
            public UnFreezedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UNFREEZED_EVENT, log);
                UnFreezedEventResponse typedResponse = new UnFreezedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<UnFreezedEventResponse> unFreezedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNFREEZED_EVENT));
        return unFreezedEventFlowable(filter);
    }

    public List<UnlockUserEventResponse> getUnlockUserEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UNLOCKUSER_EVENT, transactionReceipt);
        ArrayList<UnlockUserEventResponse> responses = new ArrayList<UnlockUserEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UnlockUserEventResponse typedResponse = new UnlockUserEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UnlockUserEventResponse> unlockUserEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UnlockUserEventResponse>() {
            @Override
            public UnlockUserEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UNLOCKUSER_EVENT, log);
                UnlockUserEventResponse typedResponse = new UnlockUserEventResponse();
                typedResponse.log = log;
                typedResponse.who = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UnlockUserEventResponse> unlockUserEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNLOCKUSER_EVENT));
        return unlockUserEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> allowance(String tokenOwner, String spender) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new Address(160, tokenOwner),
                new Address(160, spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> allowed(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLOWED, 
                Arrays.<Type>asList(new Address(160, param0),
                new Address(160, param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String spender, BigInteger tokens) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Address(160, spender),
                new Uint256(tokens)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> approveAndCall(String spender, BigInteger tokens, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVEANDCALL, 
                Arrays.<Type>asList(new Address(160, spender),
                new Uint256(tokens),
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String tokenOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new Address(160, tokenOwner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burn(BigInteger value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BURN, 
                Arrays.<Type>asList(new Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> freeze() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FREEZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> lockUser(String who) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LOCKUSER, 
                Arrays.<Type>asList(new Address(160, who)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger tokens) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(160, to),
                new Uint256(tokens)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferAnyERC20Token(String tokenAddress, BigInteger tokens) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERANYERC20TOKEN, 
                Arrays.<Type>asList(new Address(160, tokenAddress),
                new Uint256(tokens)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String _from, String to, BigInteger tokens) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new Address(160, _from),
                new Address(160, to),
                new Uint256(tokens)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unfreeze() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNFREEZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unlockUser(String who) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNLOCKUSER, 
                Arrays.<Type>asList(new Address(160, who)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SUIBEToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SUIBEToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SUIBEToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SUIBEToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SUIBEToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SUIBEToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SUIBEToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SUIBEToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SUIBEToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SUIBEToken.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SUIBEToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SUIBEToken.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SUIBEToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SUIBEToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SUIBEToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SUIBEToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String tokenOwner;

        public String spender;

        public BigInteger tokens;
    }

    public static class FreezedEventResponse extends BaseEventResponse {
    }

    public static class LockUserEventResponse extends BaseEventResponse {
        public String who;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String _from;

        public String _to;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokens;
    }

    public static class UnFreezedEventResponse extends BaseEventResponse {
    }

    public static class UnlockUserEventResponse extends BaseEventResponse {
        public String who;
    }
}
