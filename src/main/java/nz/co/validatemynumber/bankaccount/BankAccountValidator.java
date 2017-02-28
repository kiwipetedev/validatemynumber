package nz.co.validatemynumber.bankaccount;

public abstract class BankAccountValidator
{
    public abstract boolean isValid(String bankCode, String branch, String account, String suffix);
    
    public int calculateSum(int[] weightFactors, String bankAccount)
    {
        int sum = 0;
        
        for (int i = 0; i < weightFactors.length; i++)
        {
            sum += weightFactors[i] * Integer.parseInt(bankAccount.substring(i, i + 1));
        }
        
        return sum;
    }
}
