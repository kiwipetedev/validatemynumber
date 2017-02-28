package nz.co.validatemynumber.bankaccount;

public class AlgorithmFValidator extends BankAccountValidator
{
    private static final int[] WEIGHT_FACTORS = {0, 0, 0, 0, 0, 0, 0, 1, 7, 3, 1, 7, 3, 1, 0, 0, 0, 0};
    private static final int MODULUS = 10;
    
    @Override
    public boolean isValid(String bankCode, String branch, String account, String suffix)
    {
        String bankAccount = bankCode.concat(branch).concat(account).concat(suffix);
        int sum = calculateSum(WEIGHT_FACTORS, bankAccount);
        int remainder = sum % MODULUS;
        return remainder == 0;
    }
}
